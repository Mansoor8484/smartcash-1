CREATE TABLE [dbo].[Account] (
    [AccountID]     INT   NOT NULL,
    [UserID]        INT   NOT NULL,
    [Balance]       MONEY NULL,
    [RoutingNumber] INT   NULL,
    [AccountNumber] INT   NULL,
    CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED ([AccountID] ASC),
    CONSTRAINT [FK_Account_UserID] FOREIGN KEY ([UserID]) REFERENCES [dbo].[User] ([UserID])
);


GO

