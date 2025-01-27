package se.michaelthelin.spotify;


import com.thoughtworks.xstream.XStream;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.enums.Modality;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.AudioAnalysis;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioAnalysisForTrackRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class KillMutants {
//    private final GetAudioAnalysisForTrackRequest defaultRequest = ITest.SPOTIFY_API
//            .getAudioAnalysisForTrack(ITest.ID_TRACK)
//            .setHttpManager(
//                    TestUtil.MockedHttpManager.returningJson(
//                            "requests/data/tracks/GetAudioAnalysisForTrackRequest.json"))
//            .build();
//
//    public KillMutants() throws Exception {
//    }
//
//    @Test
//    public void shouldReturnDefault_sync() throws IOException, SpotifyWebApiException, ParseException {
//        AudioAnalysis aa= defaultRequest.execute();
//        shouldReturnDefault(aa);
////        System.err.println(new XStream().toXML(aa));
//    }
//
//    public void shouldReturnDefault(final AudioAnalysis audioAnalysis) {
//        // newly added assertions
//        // this kills mutation (1) '-1749212862' (9) -1899067401 (10) -1899067308 (58) 572983363
//        assertNotNull(audioAnalysis.getSegments()[0]);
//        // this kills mutation (2) '-1772382205' mutation (47) 532800659  mutation (48) 532800752 (59) 682657213
//        assertNotNull(audioAnalysis.getSections()[0]);
//        assertNotNull(audioAnalysis.getSegments()[0].getTimbre()); //this kills mutation (3) '-1899062751'
//        assertNotNull(audioAnalysis.getSegments()[0].getPitches()); // this kills mutation (4) '-1899063588'
//        assertNotNull(audioAnalysis.getSegments()[0].getLoudnessStart()); // this kills mutation (5) -1899064611
//        assertNotNull(audioAnalysis.getSegments()[0].getLoudnessMaxTime()); // this kills mutation (6) -1899065324
//        assertNotNull(audioAnalysis.getSegments()[0].getLoudnessMax()); // this kills mutation (7) -1899066037
//        assertNotNull(audioAnalysis.getSegments()[0].getLoudnessEnd()); // this kills mutation (8) -1899066750
//
//        assertNotNull(audioAnalysis.getMeta().getTimestamp()); // this kills mutation (11) -2001569486
//        assertNotNull(audioAnalysis.getMeta().getStatusCode()); //this kills mutation (12) -2001570199
//        assertNotNull(audioAnalysis.getMeta().getPlatform());// this kills mutation (13) -2001570881
//        assertNotNull(audioAnalysis.getMeta().getInputProcess()); // this kills mutation (14) -2001571563
//        assertNotNull(audioAnalysis.getMeta().getDetailedStatus()); // this kills mutation (15) -2001572245
//        assertNotNull(audioAnalysis.getMeta().getAnalyzerVersion()); // this kills mutation (16) -2001572927
//        assertNotNull(audioAnalysis.getMeta().getAnalysisTime()); // this kills mutation (17) -2001573640
//
//        // this kills mutation (18) -420252573 (multiple choices) and  (22) -570104322 and (23) -570104415
//        // and mutation (46) 285076451
//        assertNotNull(audioAnalysis.getTatums()[0]);
//        assertNotNull(audioAnalysis.getBars()[0].getStart()); // this kills mutation (19) -570102338 (multiple choices)
//        assertNotNull(audioAnalysis.getBars()[0].getDuration()); // this kills mutation (20) -570103051 (multiple choices)
//        assertNotNull(audioAnalysis.getBars()[0].getConfidence()); // this kills mutation (21) -570103764
//
//
//
////                '-645338457',
//        // mutation (24) -645335605
//        assertNotNull(audioAnalysis.getTrack().getTimeSignatureConfidence());
//        assertNotNull(audioAnalysis.getTrack().getTimeSignature());// mutation (25) -645336318'
//        assertNotNull(audioAnalysis.getTrack().getTempoConfidence());// mutation (26) -645337031
//        assertNotNull(audioAnalysis.getTrack().getTempo());//mutation (27) -645337744
//        assertNotNull(audioAnalysis.getTrack().getSynchVersion());// mutation (28) -645338457
//
//        assertNotNull(audioAnalysis.getTrack().getSynchString()); // mutation (29)-645339139
//        assertNotNull(audioAnalysis.getTrack().getStartOfFadeOut());// mutation (30) -645339852
//        assertNotNull(audioAnalysis.getTrack().getSampleMd5());// mutation (31) -645340534
//        assertNotNull(audioAnalysis.getTrack().getRhythmVersion());// mutation (32) '-645341247',
//
//        assertNotNull(audioAnalysis.getTrack().getRhythmString());// mutation (33) '-645341929',
//        assertNotNull(audioAnalysis.getTrack().getOffsetSeconds());// mutation (34) '-645342642',
//        assertNotNull(audioAnalysis.getTrack().getNumSamples());// mutation (35) '-645343355',
//        assertNotNull(audioAnalysis.getTrack().getModeConfidence());// mutation (36) '-645344068',
//
//        assertNotNull(audioAnalysis.getTrack().getLoudness());// mutation (37) -645345618
//        assertNotNull(audioAnalysis.getTrack().getKeyConfidence());// mutation (38) -645346331
//        assertNotNull(audioAnalysis.getTrack().getKey());// mutation (39） -645347044
//        assertNotNull(audioAnalysis.getTrack().getEchoprintVersion());// mutation (40) -645348470
//        assertNotNull(audioAnalysis.getTrack().getEchoprintString());// mutation (41) -645349152
//        assertNotNull(audioAnalysis.getTrack().getDuration()); // mutation (42) -645349865
//        assertNotNull(audioAnalysis.getTrack().getCodeVersion());// mutation (43) -645350578
//        assertNotNull(audioAnalysis.getTrack().getAnalysisSampleRate());// mutation (44) -645351973
//        assertNotNull(audioAnalysis.getTrack().getAnalysisChannels());/// mutation (45) -645352686
//
//        assertNotNull(audioAnalysis.getSections()[0].getKey());// mutation (49) 532801310
//        assertNotNull(audioAnalysis.getSections()[0].getKeyConfidence());// mutation (50) 532802023
//        assertNotNull(audioAnalysis.getSections()[0].getLoudness()); // mutation (51) 532802736
//        assertNull(audioAnalysis.getSections()[0].getMode());// mutation (52) 532803759
//        assertNotNull(audioAnalysis.getSections()[0].getModeConfidence());// mutation (53) 532804596
//        assertNotNull(audioAnalysis.getSections()[0].getTempo());// mutation (54) 532805309
//        assertNotNull(audioAnalysis.getSections()[0].getTempoConfidence());// mutation (55) 532806022
//        assertNotNull(audioAnalysis.getSections()[0].getTimeSignature());// mutation (56) 532806735
//        assertNotNull(audioAnalysis.getSections()[0].getTimeSignatureConfidence());// mutation (57) 532807448
//
//        // original assertions
//        assertEquals(
//                1,
//                audioAnalysis.getBars().length);
//        assertEquals(
//                1,
//                audioAnalysis.getBeats().length);
//        assertNotNull(
//                audioAnalysis.getMeta());
//        assertEquals(
//                1,
//                audioAnalysis.getSections().length);
//        assertEquals(
//                1,
//                audioAnalysis.getSegments().length);
//        assertEquals(
//                1,
//                audioAnalysis.getTatums().length);
//        assertNotNull(
//                audioAnalysis.getTrack(),
//                "");
//        assertEquals(
//                Modality.MINOR,
//                audioAnalysis.getTrack().getMode());
//    }

}
