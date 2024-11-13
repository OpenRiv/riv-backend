/*
 * MIT License
 *
 * Copyright (c) 2018 FourScouts B.V.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package nl.fourscouts.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static nl.fourscouts.security.OAuth2UserAgentUtils.withUserAgent;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // OAuth2 로그인 기능에 의해 디스코드 로그인 페이지로 리다이렉트
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
				.tokenEndpoint().accessTokenResponseClient(accessTokenResponseClient())
				.and()
				.userInfoEndpoint()
				// .userService(userService());
				.userService(customOAuth2UserService());;
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();

		client.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter() {
			@Override
			public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest oauth2Request) {
				return withUserAgent(Objects.requireNonNull(super.convert(oauth2Request))); //헤더에 agent 포함
			}
		});

		return client;
	}

	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
		DefaultOAuth2UserService service = new DefaultOAuth2UserService();

		service.setRequestEntityConverter(new OAuth2UserRequestEntityConverter() {
			@Override
			public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
				return withUserAgent(Objects.requireNonNull(super.convert(userRequest)));
			}
		});

		return service;
	}

	// 만든 거
	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
		return new DefaultOAuth2UserService() {
			@Override
			public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
				OAuth2User oAuth2User = super.loadUser(userRequest);
				Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

				// 디스코드의 `username` 속성을 `name` 필드로 매핑
				String userNameAttributeName = "username";
				return new DefaultOAuth2User(
						oAuth2User.getAuthorities(),
						attributes,
						userNameAttributeName
				);
			}
		};
	}
}
