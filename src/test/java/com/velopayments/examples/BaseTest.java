/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.velopayments.examples;

/**
 * Base Test Class - sets API credentials from System or Maven properties
 */
public class BaseTest {

    protected String apiKey;
    protected String apiSecret;
    protected String payorId;

    /**
     * Set Values via System Environment Variables or in Maven - settings.xml
     *
     * settings.xml example:
     *
     *     <profiles>
     *         <profile>
     *             <id>velosdk</id>
     *             <activation>
     *                 <activeByDefault>true</activeByDefault>
     *             </activation>
     *             <properties>
     *                 <API_KEY>your key here</API_KEY>
     *                 <API_SECRET>7your secret here</API_SECRET>
     *                 <PAYOR_ID>your payor id here</PAYOR_ID>
     *             </properties>
     *         </profile>
     *      </profiles>
     *
     */
    public void getEnvVariables(){

        apiKey = System.getenv("VELO_API_APIKEY");
        apiSecret = System.getenv("VELO_API_APISECRET");
        payorId = System.getenv("VELO_API_PAYORID");

        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getProperty("VELO_API_APIKEY");
        }

        if (apiSecret == null || apiSecret.isEmpty()) {
            apiSecret = System.getProperty("VELO_API_APISECRET");
        }

        if (payorId == null || payorId.isEmpty()) {
            payorId = System.getProperty("VELO_API_PAYORID");
        }

        if (apiKey == null || apiKey.isEmpty()){
            System.out.println("#######  WARNING API KEY NOT SET  #######");
        }

        if (apiSecret == null || apiSecret.isEmpty()){
            System.out.println("#######  WARNING API SECRET NOT SET  #######");
        }

        if (payorId == null || payorId.isEmpty()){
            System.out.println("#######  WARNING PAYEE ID NOT SET  #######");
        }
    }
}
