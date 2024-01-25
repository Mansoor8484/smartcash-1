CREATE DATABASE Smart_Cash_database;
go
--Table:USER
CREATE TABLE User(
    UserID    int,
    FirstName   char(20),
    LastName    char(20),
    Address     char(30),
    City        char(20),
    State       char(2),
    ZipCode     int,
    CellPhone   VarChar(10),
    Email       Varchar(30),
    DOB         date,
    --primary Key:
    constraint UserID_proceed_pk Primary key(UserID)
);

--Table: ACCOUNT
CREATE TABLE Account(
    AccountID   int,
    UserID      int,
    Balance     decimal(4,2),
    RoutingNumber   int,
    AccountNumber   int,
    --primary key:
    constraint Account_accountID_pk Primary key(AccountID)
    --foreign key:
    constraint Account_UserID_fk foreign key (UserID) references User(UserID)
        on update cascade
        on delete no action
);
--Table: BUDGET
CREATE TABLE Budget(
    BudgetID    int,
    UserID      int,
    Category    Char(20),
    PlannedAmount   Decimal(4,2),
    ActualAmount    Decimal(4,2),
    --primary Key:
    constraint BudgetID_proceed_pk Primary key(BudgetID)
);

--Table: USERAUTHENTICATION
CREATE TABLE UserAuthentication(
    UserID      int,
    Email       varchar(30),
    UserName    char(30),
    HashedPassword      char(30),
    Salt        char(30)
    --key figure it out (primary/foreign)
);

--Table: PAYMENT METHOD
CREATE TABLE PaymentMethod(
    PaymentMethodID     int,
    UserID              int,
    PaymentToken        int,
    PaymentType         char(40),
    ExpiryDate          Date,
    --Primary Key:
    constraint PaymentMethodID_proceed_pk Primary Key(PaymentMethodID)
    --Foreign Key:
    constraint PaymentMethod_UserID_fk foreign Key (UserID) references User(UserID)
        on update cascade
        on delete no action
);

--Table: TRANSACTION
CREATE TABLE Transaction(
    TransactionID       int,
    AccountID           int,
    Amount              decimal(4,2),
    PendingStatus       boolean,
    TransactionDate     date,
    PostDate            date
    --Primary Key:
    constraint TransactionID_proceed_pk Primary Key (transactionID)
    --Foreign Key: 
    constraint Transaction_AccountID_fk foreign key (AccountID) references Account(AccountID)
);