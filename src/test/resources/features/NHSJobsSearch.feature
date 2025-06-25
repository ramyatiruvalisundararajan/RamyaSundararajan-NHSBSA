Feature: NHS Jobs Search Functionality

Scenario: Search for jobs by title and location
  Given I am on the NHS Jobs search page
  When I enter "Nurse" in the job title field
  And I enter "Manchester" in the location field
  And I click on the Search button
  And I select "Date Posted (newest)" from the sort dropdown
  Then I should see job listings related to "Nurse"
  And the search results are sorted by Date Posted newest
  And the job listings should be sorted by the newest date posted first

Scenario: Search with empty job title and location
  Given I am on the NHS Jobs search page
  When I enter "" in the job title field
  And I enter "" in the location field
  And I click on the Search button
  Then I should see job listings

Scenario: Search with invalid location
  Given I am on the NHS Jobs search page
  When I enter "Nurse" in the job title field
  And I enter "xyz123" in the location field
  And I click on the Search button
  Then I should see the location not found header "Location not found"

Scenario: Search with partial job title
  Given I am on the NHS Jobs search page
  When I enter "nur" in the job title field
  And I enter "Manchester" in the location field
  And I click on the Search button
  Then I should see a message "No result found"

Scenario: Search and sort by "Closing Date"
  Given I am on the NHS Jobs search page
  When I enter "Nurse" in the job title field
  And I enter "Manchester" in the location field
  And I click on the Search button
  And I select "Closing Date" from the sort dropdown
  Then the search results are sorted by Closing Date
