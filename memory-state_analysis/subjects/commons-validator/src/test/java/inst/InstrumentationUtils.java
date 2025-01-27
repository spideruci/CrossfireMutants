package inst;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.IBANValidator;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


public class InstrumentationUtils {

    public static XStream allXstream = new XStream() {
        @Override
        protected MapperWrapper wrapMapper(MapperWrapper next) {

            return new MapperWrapper(next) {


                @Override
                public String serializedMember(Class type, String memberName) {
                    // Combine the class name and field name for the node
                    return "f." + super.serializedMember(type, memberName);
                    //return type.getCanonicalName() + "." + memberName;
                }

                @Override
                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                    if (
                            definedIn.getName().contains("Thread") ||definedIn.getName().startsWith("java.util.logging")
                                    || definedIn.getName().contains("EnhancerByMockito") || fieldName.equals("httpManager")
                    ){
                        return false;
                    }
                    return super.shouldSerializeMember(definedIn, fieldName);
                }
            };
        }
    };


    /*
    true: MR state
     */
    public static boolean globalState;

    // before mutation is executed, how many times methods are entered
    public static AtomicLong be = new AtomicLong(0);

    // how many times methods are entered
    public static AtomicLong ge = new AtomicLong(0);

    public static void updateN() {
        ge.getAndAdd(1);
        if (globalState) {
            //globalState means MR state
            if (eProbeMR.get() == 0) {
                be.getAndAdd(1);
            }
        }
    }

    public static Object[] _states;
    public static Set<String> processedStaticFields = new HashSet<>();

    public static List<Object> afterAllStates;
    public static List<Object> afterAllStatesInfo;

    public static XStream xstream = new XStream() {
        @Override
        protected MapperWrapper wrapMapper(MapperWrapper next) {

            return new MapperWrapper(next) {


                @Override
                public String serializedMember(Class type, String memberName) {
                    // Combine the class name and field name for the node
                    return "f." + super.serializedMember(type, memberName);
                    //return type.getCanonicalName() + "." + memberName;
                }

                @Override
                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                    if (
                            definedIn.getName().contains("Thread") ||definedIn.getName().startsWith("java.util.logging")
                                    || definedIn.getName().contains("EnhancerByMockito") || fieldName.equals("httpManager")
                    ){
                        return false;
                    }
                    return super.shouldSerializeMember(definedIn, fieldName);
                }
            };
        }
    };

    static {
        xstream.ignoreUnknownElements();
    }

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("target/staticFields.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                //filter JCL classes out
                String[] lineSplits = line.split(" ");
                String className = lineSplits[1];
                className = className.replace("/", ".");
                String fieldName = lineSplits[2];
                if (className.contains("SpotifyHttpManager")) {
                    continue;
                } // only for spotify, since this is may be mocked
                processedStaticFields.add(className + " " + fieldName);
            }
        } catch (Exception e) {
            System.err.println("Error: wrong in processing static fields in BeforeAll");
            e.printStackTrace();
        }
    }


    //if the mutation has been executed
    public static AtomicLong eProbeMR = new AtomicLong(0);
    //how many times the methods have been visited before the mutation is executed
    public static AtomicLong NeProbeMR = new AtomicLong(0);
    //How many times the methods have been visited globally.
    public static AtomicLong NgProbeMR = new AtomicLong(0);
    //How many times the mutation has been executed in total.
    public static AtomicLong NProbeMR = new AtomicLong(0);
    //if the states have been dumped?
    public static AtomicLong dumpedMR = new AtomicLong(0);

    public static AtomicLong NProbeNMR = new AtomicLong(0);

    public static AtomicLong dumpedNMR = new AtomicLong(0);

    public static AtomicLong whichTest = new AtomicLong(0);

    public static AtomicLong loopN = new AtomicLong(0);

    public static void clearStatic()  {
        try{
            IBANValidator.DEFAULT_IBAN_VALIDATOR = new IBANValidator();
        } catch (RuntimeException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex.toString(), ex);
        }
    }


    public static void initializeProbesPerTest() {
        if (isMRState()) {
            globalState = true;
        }
        //MRProbes
        eProbeMR.set(0);
        NeProbeMR.set(0);
        NgProbeMR.set(0);
        NProbeMR.set(0);
        dumpedMR.set(0);
        //NMRProbes
        NProbeNMR.set(0);
        dumpedNMR.set(0);
        // the length
        ge.set(0);
        be.set(0);

        if (isNMRState()) {
            setWhichTest();
            setLoopN();

        }
    }

    public static void dumpProbesPerTest() {
        if (isMRState()) {
            clearFile("target/MR.txt");
            clearFile("target/len.txt");
            String MR_info = eProbeMR.toString() + " " + NeProbeMR.toString() +
                    " " + NgProbeMR.toString() + " " + NProbeMR.toString() +
                    " " + dumpedMR.toString();
            addOneLine(MR_info, "target/MR.txt");
            String len_info = be.toString() + " " + ge.toString();
            addOneLine(len_info, "target/len.txt");
        } else if (isNMRState()) {
            clearFile("target/NMR.txt");
            clearFile("target/len.txt");
            String NMR_info = NProbeNMR.toString() + " " + dumpedNMR.toString();
            addOneLine(NMR_info, "target/NMR.txt");
            String len_info = ge.toString();
            addOneLine(len_info, "target/len.txt");
        }
    }


    public static boolean isMRState() {
        String res = readOneLine("target/GlobalStates.txt");
        if (res.equals("1")) {
            return true;
        }
        return false;
    }

    public static boolean isNMRState() {
        String res = readOneLine("target/GlobalStates.txt");
        if (res.equals("0")) {
            return true;
        }
        return false;
    }



    /**
     * invoked if the mutation is going to be executed
     */
    public static void setEProbeMR() {
        eProbeMR.set(1);
    }

    public static void visitNEProbeMR() {
        if (eProbeMR.get() == 0) {
            NeProbeMR.incrementAndGet();
        }
    }

    public static void visitNGProbeMR() {
        NgProbeMR.incrementAndGet();
    }

    public static void visitNProbeMR() {
        NProbeMR.incrementAndGet();
    }

    public static void setDumpedMR() {
        dumpedMR.set(1);
    }




    /**
     * if the states should be dumped
     * 1: mutation executed, 2: states haven't been dumped, 3: current visit
     * @return 0 should be dumped
     */
    public static int shouldDumpStateForMR() {
        if (eProbeMR.get() == 1 && dumpedMR.get() != 1 && NeProbeMR.get() == NgProbeMR.get()) {
            return 0;
        }
        return 1;
    }


    public static void setLoopN() {
        String MRInfo = readLineFromFile("target/MRs.txt", whichTest.intValue());
        if (MRInfo == null) {
            System.err.println("MRinfo is null");
            return;
        }
        String[] results = MRInfo.split(" ");
        loopN.set(Long.parseLong(results[1]));
    }

    public static void setWhichTest() {
        whichTest.set(Long.valueOf(readOneLine("target/WhichTest.txt")));
    }

    /**
     * if the states should be dumped for NMR
     * 1. states hasn't been dumped 2. NprobeNMR == NeProbeMR
     * @return
     */
    public static int shouldDumpStateForNMR() {
        if (dumpedNMR.get() == 0 && loopN.get() == NProbeNMR.get()) {
            return 0;
        }
        return 1;
    }

    public static void clearFile(String filePath) {
        checkExistsOrCreate(filePath);
        try {

            // Create a new FileWriter object that points to the text file
            FileWriter writer = new FileWriter(filePath);

            // Overwrite the contents of the text file with an empty string
            writer.write("");

            // Close the file and save the changes
            writer.close();
        } catch (IOException e) {
            // Handle the exception
        }
    }

    public static void checkExistsOrCreate(String filePath) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static synchronized void addOneLine(String info, String filePath) {
        File targetFile = new File(filePath);
        // Check if the file exists, and create it if it doesn't
        checkExistsOrCreate(filePath);

        // Append a line to the end of the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, true))) {
            bw.write(info);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            //bug
            System.err.println("There is a null pointer exception here");
        }
    }


    public static String readOneLine(String filePath) {
        try {
            // Create a new FileReader object that points to the text file
            FileReader reader = new FileReader(filePath);

            // Wrap the FileReader object in a BufferedReader object
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Read a single line of text from the file
            String line = bufferedReader.readLine();

            // Close the file and release any resources associated with it
            bufferedReader.close();
            return line;
        } catch (IOException e) {
            // Handle the exception
        }
        return null;
    }


    /**
     * dump each object from the object list
     * @param o
     * @param filename
     * @param parentFolder
     * @param secondFolder
     */

    public static synchronized void dumpObjectUsingXml(Object o,String filename,String parentFolder, String secondFolder) {


        // avoid gc limit in coverage run, can be turned on/off
        if (!isMRState() && !isNMRState()) {
            return;
        }

        xstream.ignoreUnknownElements();
        // create output directory
        String base = "target/xmlOutput/states";
        try {
            File directory = new File(base);
            directory.mkdirs();
        } catch (Exception e) {
            addOneLine("fail to create folder", "target/xmlOutput/errInfo");
        }

        // convert to object list
        List<Object> list = (List<Object>) o;
        Object[] obArray = new Object[processedStaticFields.size()];
        int total = list.size() + obArray.length;
        if (total > 10000) {
            addOneLine("TooManyStates: " + total, base + File.separator + "state_num.txt");
            return;
        } else {

            addOneLine("AcceptableStates: " + total, base + File.separator + "state_num.txt");
        }


        // dump each object one by one
        for (int i = 0; i < list.size(); i++) {
            try {
                String path = base + File.separator + "states " + i + ".xml";
                FileWriter fw = new FileWriter(path);
                xstream.toXML(list.get(i), fw);
                if (list.get(i) == null) {
                    addOneLine("null" + " success\n" + afterAllStatesInfo.get(i), base + File.separator + i + " info.txt");
                } else {
                    addOneLine(list.get(i).getClass().getName() + " success\n" + afterAllStatesInfo.get(i), base + File.separator + i + " info.txt");
                }
            } catch (Throwable t) {
                if (list.get(i) == null) {
                    addOneLine("null" + " fail\n" + afterAllStatesInfo.get(i), base + File.separator + i + " info.txt");
                } else {
                    addOneLine(list.get(i).getClass().getName() + " fail\n" + afterAllStatesInfo.get(i), base + File.separator + i + " info.txt");
                }
            }
        }

        //prepare static fields

        int i = 0;
        try {
            for (String content : processedStaticFields) {
                String[] content_splits = content.split(" ");
                String className = content_splits[0];
                String fieldName = content_splits[1];
                Class<?> clazz = Class.forName(className);
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(null);
                obArray[i] = value;
                i++;
            }
        } catch (Throwable e) {
            System.err.println("Error: wrong in using Xstream in AfterAll");
            String info = "";
            info = info + e.getMessage() + e.getClass().getName();
            for (StackTraceElement s : e.getStackTrace()) {
                info = info + s.getFileName() + " " + s.getMethodName() + " " + s.getLineNumber();
            }
            addOneLine("ha:there is something wrong when using XStream in afterall, the reason is: \n", "target/XStreamError.txt");
            e.printStackTrace();
            System.err.println("Error: wrong in using reflection in BeforeAll");
            e.printStackTrace();
        }

        int j = list.size();
        for (Object ob:obArray) {
            try {
                String path = base + File.separator + "states " + j + ".xml";
                FileWriter fw = new FileWriter(path);
                xstream.toXML(ob, fw);
                if (ob == null) {
                    addOneLine("null" + " pass\n" + "somethingclass nomethod 0 statics", base + File.separator + j + " info.txt");
                } else {
                    addOneLine(ob.getClass().getName() + " pass\n" + "somethingclass nomethod 0 statics", base + File.separator + j + " info.txt");
                }
            } catch (Throwable t) {
                if (ob == null) {
                    addOneLine("null" + " fail\n" + "somethingclass nomethod 0 statics", base + File.separator + j + " info.txt");
                } else {
                    addOneLine(ob.getClass().getName() + " fail\n" + "somethingclass nomethod 0 statics", base + File.separator + j + " info.txt");
                }
            }
            j++;
        }
        //dump static fields

        //dump everything once
        Object[] all = new Object[total];
        for (int x = 0; x < list.size(); x++) {
            all[x] = list.get(x);
        }
        for (int x = list.size(); x < total; x++) {
            all[x] = obArray[x - list.size()];
        }

        try {
            String path = base + File.separator + "all_states.xml";
            FileWriter fw = new FileWriter(path);
            xstream.toXML(all, fw);
            addOneLine("null" + " success\n" + "all_states", base + File.separator +  "all_states_info.txt");

        } catch (Throwable t) {
            addOneLine("null" + " fail\n" + "all_states", base + File.separator +  "all_states_info.txt");
        }


        try {
            String path = base + File.separator + "all_xstream_states.xml";
            FileWriter fw = new FileWriter(path);
            allXstream.toXML(all, fw);
            addOneLine("null" + " success\n" + "all_states", base + File.separator +  "all_xstream_states_info.txt");

        } catch (Throwable t) {
            addOneLine("null" + " fail\n" + "all_states", base + File.separator +  "all_xstream_states_info.txt");
        }

        clearStatic();
    }


    public static void visitNProbeNMR() {
        NProbeNMR.incrementAndGet();
    }

    public static void setDumpedNMR() {
        dumpedNMR.set(1);
    }


    public static String readLineFromFile(String fileName, int lineNumber) {
        String line = null;
        lineNumber = lineNumber + 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < lineNumber; i++) {
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return line;
    }

    public static void no_use() {
        dumpObjectUsingXml(null, "states", "target", "xmlOutput");
    }
}
