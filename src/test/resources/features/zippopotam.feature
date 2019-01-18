Feature: QA Coding Challenge - III
    Upgrade exposes an API that returns eligible states (and state specific criteria) to apply for a loan. See: https://credapi.credify.tech/api/loana
    pp/v1/states

    @testingsteps
    Scenario: Verify that information provided by this API is as per the specifications
        Given Request from test page
        When Get test page
        And I get status code 200
        And I get the headers
        Then Validate all the state names are valid
        And Validate total state count is "47"
        And Validate only one state has a min age of "19" , and output the name of that state
        And Validate Georgia is the only state with min loan amount of "3.005"

