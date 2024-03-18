CREATE TABLE [dbo].[Budget] (
    [BudgetID]      INT            NOT NULL,
    [UserID]        INT            NOT NULL,
    [Category]      CHAR (20)      NULL,
    [PlannedAmount] DECIMAL (4, 2) NULL,
    [ActualAmount]  DECIMAL (4, 2) NULL,
    CONSTRAINT [PK_Budget_BudgetID] PRIMARY KEY CLUSTERED ([BudgetID] ASC),
    CONSTRAINT [FK_Budget_UserID] FOREIGN KEY ([BudgetID]) REFERENCES [dbo].[User] ([UserID]) ON UPDATE CASCADE
);


GO

