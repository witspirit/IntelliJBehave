Narrative:
Testing a search result size

Meta:
@Suite smoke testing
Scenario: open a url

Given Open url 'http://some.url/path'
When search for 'something'
Then result list size is 10
<warning descr="Step 'Then check result list size is 10' is not defined">Then check result list size is 10</warning>
<warning descr="Step 'Then check that result contains \"an item\"' is not defined">Then check that result contains "an item"</warning>
