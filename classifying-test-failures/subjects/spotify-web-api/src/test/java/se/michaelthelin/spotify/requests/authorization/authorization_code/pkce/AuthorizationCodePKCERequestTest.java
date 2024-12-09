package se.michaelthelin.spotify.requests.authorization.authorization_code.pkce;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.ITest;
import se.michaelthelin.spotify.TestUtil;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.michaelthelin.spotify.Assertions.assertHasBodyParameter;
import static se.michaelthelin.spotify.Assertions.assertHasHeader;

public class AuthorizationCodePKCERequestTest implements ITest<AuthorizationCodeCredentials> {

  private final AuthorizationCodePKCERequest defaultRequest = SPOTIFY_API.authorizationCodePKCE(AUTHORIZATION_CODE, CODE_VERIFIER)
    .setHttpManager(
      TestUtil.MockedHttpManager.returningJson(
        "requests/authorization/authorization_code/AuthorizationCode.json"))
    .build();

  public AuthorizationCodePKCERequestTest() throws Exception {
  }

  @Test
  public void shouldComplyWithReference() {
    assertHasHeader(defaultRequest, "Content-Type", "application/x-www-form-urlencoded");
    assertHasBodyParameter(
      defaultRequest,
      "code",
      AUTHORIZATION_CODE);
    assertHasBodyParameter(
      defaultRequest,
      "grant_type",
      "authorization_code");
    assertHasBodyParameter(
      defaultRequest,
      "redirect_uri",
      SPOTIFY_API.getRedirectURI());
    assertEquals(
      "https://accounts.spotify.com:443/api/token",
      defaultRequest.getUri().toString());
    assertHasBodyParameter(
      defaultRequest,
      "client_id",
      SPOTIFY_API.getClientId());
    assertHasBodyParameter(
      defaultRequest,
      "code_verifier",
      CODE_VERIFIER);
  }

  @Test
  public void shouldReturnDefault_sync() throws IOException, SpotifyWebApiException, ParseException {
    shouldReturnDefault(defaultRequest.execute());
  }

  @Test
  public void shouldReturnDefault_async() throws ExecutionException, InterruptedException {
    shouldReturnDefault(defaultRequest.executeAsync().get());
  }

  public void shouldReturnDefault(final AuthorizationCodeCredentials authorizationCodeCredentials) {
    assertEquals(
      "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk",
      authorizationCodeCredentials.getAccessToken());
    assertEquals(
      "Bearer",
      authorizationCodeCredentials.getTokenType());
    assertEquals(
      "user-read-birthdate user-read-email",
      authorizationCodeCredentials.getScope());
    assertEquals(
      3600,
      (int) authorizationCodeCredentials.getExpiresIn());
    assertEquals(
      "b0KuPuLw77Z0hQhCsK-GTHoEx_kethtn357V7iqwEpCTIsLgqbBC_vQBTGC6M5rINl0FrqHK-D3cbOsMOlfyVKuQPvpyGcLcxAoLOTpYXc28nVwB7iBq2oKj9G9lHkFOUKn",
      authorizationCodeCredentials.getRefreshToken());
  }
}
