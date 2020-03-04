# Payout Example

It is important to understand payouts are a 3 phase process. First, submit a payout. This request will have a list of 
payees and payment amounts. The result of this action will provide a payoutId. At this point the payout is created, assuming
validations passed.
Submission and validation is an asynchronous process. The payoutId Should be polled until validation is complete and the payout is in ACCEPTED status.

**Payees which have not confirmed their accounts cannot receive payments.** 

Second phase Once a payout is in ACCEPTED status, it may be Quoted. instructed.

Third phase is Instructing a payout. Instruction is the final step in sending the payments it contains to payees. Payout Instruction is performed by a POST action referencing the payoutId.

For this example to work properly:

1. Update the InvitePayeeExample with a unique, usable email. 
    > **Hint:** On many email systems you can create unique email addresses buy adding a plus and unique string after
    the email account name. john.smith+payee1@gmail.com and john.smith+payee2@gmail.com are two different email accounts 
    to Velo Payments. HOWEVER - they will both be delivered to the email account of john.smith@gmail.com.
2. Use a unique identifier for the payee's remoteId. 
3. Receive the email and complete the onboarding process.
4. Create batch payments using the unique identifier (remoteId) for each payee. 
5. Capture payoutId returned by API.
6. Poll payoutId for ACCEPTED status.
7. Quote the payout using the payoutId.
8. Instruct payout with POST action using payoutId.

This example as is, typically does not have confirmed payees, thus will fail. 

