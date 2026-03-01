package com.hieupahm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hieupahm.payload.dto.KeycloakRoleDTO;
import com.hieupahm.payload.dto.KeycloakUserDTO;
import com.hieupahm.payload.dto.KeycloakUserInfo;
import com.hieupahm.payload.request.Credential;
import com.hieupahm.payload.request.SignupDTO;
import com.hieupahm.payload.request.UserRequest;
import com.hieupahm.payload.response.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${keycloak.base.url:http://localhost:8080}")
    private String KEYCLOAK_BASE_URL;

    private String KEYCLOAK_ADMIN_API;

    private String TOKEN_URL;
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid email profile";

    @Value("${keycloak.client.id:salon-booking-client}")
    private String CLIENT_ID;

    @Value("${keycloak.client.secret}")
    private String CLIENT_SECRET;

    @Value("${keycloak.admin.username}")
    private String username;

    @Value("${keycloak.admin.password}")
    private String password;

    @Value("${keycloak.client.uuid}")
    private String clientId;

    private final RestTemplate restTemplate;

    public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken)
            throws Exception {
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("grant_type", grantType);
        requestBody.add("scope", scope);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("refresh_token", refreshToken);
        // Create HTTP entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // Send POST request
        try {
            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    TokenResponse.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public KeycloakUserDTO fetch1stUserByUsername(String username, String token) throws Exception {
        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users?username=" + username;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Send the GET request
            ResponseEntity<KeycloakUserDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    KeycloakUserDTO[].class);
            // Extract and return the first user object
            KeycloakUserDTO[] users = response.getBody();
            if (users != null && users.length > 0) {
                return users[0]; // Return the first user
            } else {
                throw new Exception("No users found for username: " + username);
            }
        } catch (Exception e) {
            throw new Exception("Failed to fetch user details: " + e.getMessage());
        }
    }

    public KeycloakRoleDTO getRoleByName(String clientId, String token, String role) throws Exception {
        // Endpoint URL
        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/clients/{clientId}/roles/{role}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + token);
        headers.set("Accept", "application/json");
        // Create the HTTP entity
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KeycloakRoleDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    KeycloakRoleDTO.class,
                    clientId,
                    role);
            return response.getBody();
        } catch (Exception e) {

            System.err.println("Client error: " + e.getMessage());
            throw new Exception(e.getMessage());

        }
    }

    public void assignRoleToUser(String userId, String clientId, List<KeycloakRoleDTO> roles, String token)
            throws Exception {
        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users/" + userId +
                "/role-mappings/clients/" + clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<List<KeycloakRoleDTO>> entity = new HttpEntity<>(roles, headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            response.getStatusCode();
        } catch (Exception e) {

            throw new Exception("Failed to assign roles: " + e.getMessage());
        }
    }

    public KeycloakUserInfo fetchUserInfoByJwt(String token) throws Exception {
        String url = KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            // Send the GET request
            ResponseEntity<KeycloakUserInfo> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    KeycloakUserInfo.class);

            // Extract and return the first user object
            return response.getBody();

        } catch (Exception e) {
            System.out.println("Failed to fetch user details: " + e.getMessage());
            throw new Exception("Failed to fetch user details: " + e.getMessage());
        }
    }

    /**
     * Sign Up account
     */

    public void createUser(SignupDTO signupDTO) throws Exception {
        // initialize derived URLs once base URL is injected
        this.KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL + "/admin/realms/master/users";
        this.TOKEN_URL = KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/token";
        String ACCESS_TOKEN = getAdminAccessToken(username, password, GRANT_TYPE, null).getAccessToken();

        System.out.println("access token: " + ACCESS_TOKEN);

        Credential credential = new Credential();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(signupDTO.getPassword());

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(signupDTO.getEmail());
        userRequest.setEnabled(true);
        userRequest.setUsername(signupDTO.getUsername());
        userRequest.getCredentials().add(credential);

        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        // Create HTTP entity
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);
        try {
            restTemplate.exchange(KEYCLOAK_ADMIN_API, HttpMethod.POST, requestEntity, String.class);

            System.out.println("User created successfully!");

            KeycloakUserDTO user = fetch1stUserByUsername(signupDTO.getUsername(), ACCESS_TOKEN);
            KeycloakRoleDTO role = getRoleByName(clientId, ACCESS_TOKEN, signupDTO.getRole().toString());
            List<KeycloakRoleDTO> roles = new ArrayList<>();
            roles.add(role);
            assignRoleToUser(user.getId(), clientId, roles, ACCESS_TOKEN);

        } catch (Exception e) {
            // Handle HTTP 4xx errors
            System.err.println("Client error: " + e.getMessage());
            throw new Exception(e.getMessage());

        }
    }

}
