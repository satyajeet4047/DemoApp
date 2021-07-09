package com.example.aislechallenge;

import com.example.aislechallenge.data.BaseRepository;
import com.example.aislechallenge.data.model.response.GetProfileResponse;
import com.example.aislechallenge.data.model.response.VerifyOTPResponse;
import com.example.aislechallenge.data.model.response.VerifyPhoneNumResponse;
import com.example.aislechallenge.data.network.AuthApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import io.reactivex.Single;
import okhttp3.mockwebserver.MockResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseRepositoryTest {

    @Mock
    AuthApiService apiService;

    BaseRepository baseRepository;

    @Before
    public void doSetup() {
        baseRepository = new BaseRepository(apiService);
    }


    @Test
    public void shouldReturnTrue_when_phoneNumberIsCorrect(){
        VerifyPhoneNumResponse actual = new VerifyPhoneNumResponse();
        actual.setStatus(true);
        when(apiService.verifyPhoneNumber(any())).thenReturn(Single.just(actual));
        Single<VerifyPhoneNumResponse> expected = baseRepository.verifyPhoneNumber(anyString());
        assertNotNull(expected);
        assertEquals(true,expected.blockingGet().getStatus());
    }

    @Test
    public void shouldReturnFalse_when_phoneNumberIsWrong(){
        VerifyPhoneNumResponse actual = new VerifyPhoneNumResponse();
        actual.setStatus(false);
        when(apiService.verifyPhoneNumber(any())).thenReturn(Single.just(actual));
        Single<VerifyPhoneNumResponse> expected = baseRepository.verifyPhoneNumber(any());
        assertNotNull(expected);
        assertEquals(false,expected.blockingGet().getStatus());
    }

    @Test
    public void shouldReturnFalse_when_phoneNumberIsNull(){
        Single<VerifyPhoneNumResponse> expected = baseRepository.verifyPhoneNumber(null);
        assertNull(expected);
    }

    @Test
    public void shouldReturnTrue_when_otpIsCorrect(){
        VerifyOTPResponse actual = new VerifyOTPResponse();
        actual.setToken("test-token");
        when(apiService.verifyOtp(any())).thenReturn(Single.just(actual));
        Single<VerifyOTPResponse> expected = baseRepository.verifyOTP(any());
        assertNotNull(expected);
        assertEquals("test-token",expected.blockingGet().getToken());
    }

    @Test
    public void shouldReturnTrue_when_otpIsWrong(){
        VerifyOTPResponse actual = new VerifyOTPResponse();
        actual.setToken(null);
        when(apiService.verifyOtp(any())).thenReturn(Single.just(actual));
        Single<VerifyOTPResponse> expected = baseRepository.verifyOTP(any());
        assertNull(expected.blockingGet().getToken());
    }

    @Test
    public void shouldReturnFalse_when_otpIsNull(){
        Single<VerifyOTPResponse> expected = baseRepository.verifyOTP(null);
        assertNull(expected);
    }


    @Test
    public void shouldReturnUserProfiles_when_accessTokenIsCorrect(){
        String actual = TestUtil.readJson("assets/user-list-response.json");
        if (actual != null) {
            BaseRepositoryMock.mockWebServer.enqueue(new MockResponse().setBody(actual));
        }
        Single<GetProfileResponse> expected =  BaseRepositoryMock.mockAuthApi.getUserProfiles("test-token");
        GetProfileResponse getProfileResponse = expected.blockingGet();
        assertNotNull(getProfileResponse);
        assertEquals("Mayank",getProfileResponse.invites.profiles.get(0).general_information.first_name);
    }

    @Test
    public void shouldReturnError_when_accessTokenIsWrong(){
        GetProfileResponse actual = new GetProfileResponse();
       when(baseRepository.getUserProfiles(anyString())).thenReturn(Single.just(actual));
       Single<GetProfileResponse> expected = baseRepository.getUserProfiles(anyString());
        assertNull(expected.blockingGet().invites);
    }


    @After
    public void tearDown() {
        baseRepository = null;
    }
}
