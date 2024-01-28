CREATE DATABASE Smart_Cash_database;

--Table:USERS
CREATE TABLE Users(
    UserID    int PRIMARY KEY,
    FirstName   varchar(20),
    LastName    varchar(20),
    Address     varchar(30),
    City        varchar(20),
    State       char(2),
    ZipCode     int,
    CellPhone   VarChar(10),
    Email       Varchar(30) Unique,
    DOB         date,
);

--Table: ACCOUNTS
CREATE TABLE Accounts(
    AccountID   int primary key,
    UserID      int,
    Balance     decimal(10,2),
    RoutingNumber   int,
    AccountNumber   int,
    --foreign key:
    Foreign Key (UserID) References Users(UserID)
        on update cascade
        on delete no action
);
--Table: BUDGETS
CREATE TABLE Budgets(
    BudgetID    int primary key,
    UserID      int,
    Category    Char(20),
    PlannedAmount   Decimal(4,2),
    ActualAmount    Decimal(4,2),
    --foreign key:
    Foreign key (UserID) References Users(UserID)
        on update cascade
        on delete no action
);

--Table: USERAUTHENTICATION
CREATE TABLE UserAuthentication(
    UserID      int primary key,
    Email       varchar(30),
    UserName    char(30),
    HashedPassword      varchar(100), --this can be adjusted as needed.
    Salt        varchar(50), --this can be adjusted as needed. 
    --foreign key:
    Foreign key (UserID) references Users(UserID)
        on update cascade
        on delete no action
);

--Table: PAYMENT METHOD
CREATE TABLE PaymentMethods(
    PaymentMethodID     int primary key,
    UserID              int,
    PaymentToken        int,
    PaymentType         char(40),
    ExpiryDate          Date,
    --Foreign Key:
    Foreign key (UserID) references Users(UserID)
        on update Cascade
        on delete no action
);

--Table: TRANSACTIONS
CREATE TABLE Transactions(
    TransactionID       int primary key,
    AccountID           int,
    Amount              decimal(10,2),
    PendingStatus       boolean,
    TransactionDate     date,
    PostDate            date

    --Foreign Key: 
    foreign key (accountID) references Accounts(AccountID)
        on update Cascade
        on delete no action
);
