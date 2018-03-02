Meta:

Narrative:
As a user
I want to create a person and insert in the database
So that I can retrive the person by passing the cpf

Scenario: creating the person and retriving from database with success
Given a person named jhon with the cpf 22
When I save this person on the database
Then I retrive this person passing the cpf 22