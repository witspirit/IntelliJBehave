Meta:

Narrative:
As a user I want to test that a specific talkgroup can be monitored

GivenStories: product_utg/testdata/@UtgContacts.story

Scenario: Booking of MEP Profiles
Given booked profiles:
|profile|group     |host            |
|MEP    |UtgClient |192.168.5.196   |

Scenario: Add Sip Contact
When adding SIP contact UtgClient

Scenario: Apply config
Given MEP UtgClient applies Monitor configuration on endpoint UtgClient

Scenario: MEP client is attempting to monitor a talkgroup
When MEP UtgClient initiates a call from SIP contact UtgClient to SIP contact UtgTg261
Then MEP UtgClient has Confirmed dialog for SIP contact UtgClient
!-- voice check in addition to be done

Scenario: MEP client is attempting to unmonitor the talkgroup
When MEP UtgClient ends the active call of SIP contact UtgClient
Then MEP UtgClient has Terminated dialog for SIP contact UtgClient
!-- no voice check in addition to be done

Scenario: Teardown/Cleanup
When MEP UtgClient ends all active calls

Scenario: Remove Sip Contact
When removing SIP contact UtgClient