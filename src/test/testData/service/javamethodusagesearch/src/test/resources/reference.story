Narrative:
Testing a search result size

Meta:
@Suite smoke testing
Scenario: open a url

Given Open url 'http://some.url/path'
When search for 'something'
Then the result list size is 30
When search for 'something else'
Then the result list size is 25