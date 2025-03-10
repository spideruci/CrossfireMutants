package se.michaelthelin.spotify.requests.data.library;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.AbstractDataTest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class RemoveUsersSavedTracksRequestTest extends AbstractDataTest<String> {
  @BeforeAll
  public static void myBeforeAll() {

  }

  @AfterAll
  public static void myAfterAll() {

  }
  private final RemoveUsersSavedTracksRequest defaultRequest = ITest.SPOTIFY_API
    .removeUsersSavedTracks(ITest.ID_TRACK, ITest.ID_TRACK)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();
  private final RemoveUsersSavedTracksRequest bodyRequest = ITest.SPOTIFY_API
    .removeUsersSavedTracks(ITest.TRACKS)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(null))
    .build();

  public RemoveUsersSavedTracksRequestTest() throws Exception {
  }

  @Test
  public void shouldComplyWithReference() {
    assertHasAuthorizationHeader(defaultRequest);
    assertEquals(
      "https://api.spotify.com:443/v1/me/tracks?ids=01iyCAUm8EvOFqVWYJ3dVX%2C01iyCAUm8EvOFqVWYJ3dVX",
      defaultRequest.getUri().toString());

    assertHasAuthorizationHeader(bodyRequest);
    assertHasHeader(defaultRequest, "Content-Type", "application/json");
    assertHasBodyParameter(bodyRequest,
      "ids",
      ITest.TRACKS);
    assertEquals("https://api.spotify.com:443/v1/me/tracks",
      bodyRequest.getUri().toString());
  }

  @Test
  public void shouldReturnDefault_sync() throws IOException, SpotifyWebApiException, ParseException {
    shouldReturnDefault(defaultRequest.execute());
  }

  @Test
  public void shouldReturnDefault_async() throws ExecutionException, InterruptedException {
    shouldReturnDefault(defaultRequest.executeAsync().get());
  }

  public void shouldReturnDefault(final String string) {
    assertNull(
      string);
  }
}
