Narrative:
Testing a search result size

Meta:
@Suite smo<caret>ke testing
Scenario: open a url

Given Open url 'http://some.url/path'
When search for 'something'
Then result list size is 30
When search for 'something else'
Then result list size is 25