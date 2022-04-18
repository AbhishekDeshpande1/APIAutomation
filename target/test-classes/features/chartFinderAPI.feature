Feature: Test to check Chart Finder will accept non PDF file

Background: 
Given User will generate token using valid credentials 

Scenario: Create the request to accept non PDF file 
When User created request and upload PDF and Non PDF file 
Then Non PDF an PDF file should upload successfully 

Scenario: Create the request to accept multiple  non PDF file 
When User created request and upload one PDF and multiple Non PDF file 
Then multiple Non PDF and PDF file should upload successfully 

Scenario: It should accept PDF file only 
When User created request and upload one PDF file only
Then It should  accept only PDF file 

Scenario: To check with invalid chartID
When User created request and upload files and enter invalid chartId
Then Error message should display for invalid chartid

Scenario: To check with invalid chart status
When User created request and upload files and enter invalid chart status
Then Error message should display for invalid chart status

Scenario: To check with different chart id
When User created request and upload files and enter different chart id
Then Success message should display and file should upload successfully

