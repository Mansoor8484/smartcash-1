CREATE TABLE [dbo].[Transaction] (
    [TransactionID]   INT      NOT NULL,
    [AccountID]       INT      NOT NULL,
    [Amount]          MONEY    NOT NULL,
    [PendingStatus]   CHAR (8) NULL,
    [TransactionDate] DATE     NULL,
    [PostDate]        DATE     NULL,
    CONSTRAINT [PK_Transaction] PRIMARY KEY CLUSTERED ([TransactionID] ASC),
    CONSTRAINT [FK_Transaction_AccountID] FOREIGN KEY ([AccountID]) REFERENCES [dbo].[Account] ([AccountID]) ON UPDATE CASCADE
);


GO

