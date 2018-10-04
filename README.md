[![CircleCI](https://circleci.com/gh/velopaymentsapi/velo-java-examples.svg?style=svg&circle-token=5dee1bff0f724a035fe59b2e2f4d87cc10e2a572)](https://circleci.com/gh/velopaymentsapi/velo-java-examples)
# Velo Payments Java Examples

This repository contains simple step by step code examples you can use to see how the API used with your API keys. 

These examples are intended to show you how to use the Velo Payments Open API for common use cases.

You can view the complete Velo Payments Open API documentation [here](https://velopaymentsapi.github.io/VeloOpenApi/).

## Getting Started
### Initial Setup
To get started using Velo Payments, you will need to setup a Funding Bank account. This is the bank account that will be used
to transfer money into your Velo Payments funding account. The Funding Bank account can be set or updated with the 
[Update Funding Account API](https://velopaymentsapi.github.io/VeloOpenApi/#operation/setPayorFundingBankDetails).

Next, you will need to submit a [ACH Funding request](https://velopaymentsapi.github.io/VeloOpenApi/#operation/payorAchFundingRequest). This will create a ACH transfer of funds into your Velo Funding account.

You can add Payees to your Velo account using the [Create Payees API](https://velopaymentsapi.github.io/VeloOpenApi/#operation/createPayees).

### Issuing Payments

Issuing payments is a two phase process. First, you create a Payout using the submit [Payout API](https://velopaymentsapi.github.io/VeloOpenApi/#operation/submitPayout). Through this API you 
submit payment instructions for multiple Payees. This API creates a 'Payout' with Velo. 

The second step of issuing payments is to call the [Instruct Payout API](https://velopaymentsapi.github.io/VeloOpenApi/#operation/payoutInstruct). This action tells Velo to execute the payment instructions.  

## Examples
### Initial Setup
1. [Authorization](https://github.com/velopaymentsapi/velo-java-examples/tree/master/src/main/java/com/velopayments/examples/authorization) 
This example shows you how to use your API keys to get an authorization token from the Velo Payments Authorization API.
2. [Update Funding Account](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/payorservice/SetPayorFundingBankDetailsExample.java) 
Update your funding account for Velo Payments.
3. [ACH Funding request](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/payorservice/AchFundingRequestExample.java)
Example showing you how to initiate a ACH funding request to your Velo Payments funding account.
4. [Invite Payees API](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/payeeservice/InvitePayeeExample.java)
This example shows you how to invite payees to Velo Payments. 

### Issuing Payouts
1. [Payout Example](http://example.com) - **to do** complete


### Other API Examples
1. [Get List of Payees](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/payeeservice/GetPayeesExample.java)
Example showing you how to get a list of Payees. 
2. [Get Payor Details](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/payorservice/GetPayorDetailsByIdExample.java)
Get details of your payor account with Velo Payments.
3. [Get Source Accounts](https://github.com/velopaymentsapi/velo-java-examples/blob/master/src/main/java/com/velopayments/examples/fundingmanager/GetSourceAccountsExample.java) - **to do** complete
Get Funding Source Accounts listed with Velo Payments.