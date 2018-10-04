# Authorization Example

In this example, you can see how to get an authorization token from Velo.

Create a string comprising the API key (e.g. abcdef) and API secret (e.g. 123456) with a colon between them. E.g. abcdef:123456

base64 encode the string 'abcdef:123456' to - YWJjZGVmOjEyMzQ1Ngo=

Create an HTTP Authorization header with the value set to e.g. Basic YWJjZGVmOjEyMzQ1Ngo=

Send a HTTP Post to the Velo Payments Authentication API, and extract auth token from response. 

This token can be used for additional API calls.