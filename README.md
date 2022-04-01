# Advanced Object Oriented Programming Project

This project simulates a bank system where clients can create accounts and transfer money using their cards.

# Objects

- **Account** -- abstract object which has the personal information of the client and keeps track of their cards, savings and money.
  - **BasicAccount** -- object representing a normal account with no benefits.
  - **PremiumAccount** -- object representing a premium account for our special customers with payback implemented (at each transaction they receive back a part of the money used).
- **Card** -- abstract object which retains the information of a bank card. 
  - **DebitCard** -- object representing a debit card which cannot have a balance under 0.
  - **CreditCard** -- object representing a credit card which can have a negative balance up to a certain limit.
- **Savings** -- object retaining the savings of an account.
- **Transaction** -- object representing a transaction between 2 accounts (keeps tract of from and to which accounts an amount is moved).

# Actions

The console app has a menu consisting of 13 actions:
1. Create an account.
2. Search an account by email.
3. List all accounts in the bank sorted by balance.
4. Update details of an account.
5. Delete an account.
6. Create a card.
7. List all cards of an account.
8. Add money to card.
9. Withdraw money from card.
10. Delete a card.
11. Add money to savings.
12. Make a transaction.
13. Quit.
