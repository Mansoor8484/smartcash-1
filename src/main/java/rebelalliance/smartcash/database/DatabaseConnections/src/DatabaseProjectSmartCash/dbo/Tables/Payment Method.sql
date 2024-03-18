CREATE TABLE [dbo].[Payment Method] (
    [PaymentMethodID] INT       NOT NULL,
    [UserID]          INT       NOT NULL,
    [PaymentToken]    INT       NULL,
    [PaymentType]     CHAR (40) NULL,
    [ExpiryDate]      DATE      NULL,
    CONSTRAINT [PK_Payment Method] PRIMARY KEY CLUSTERED ([PaymentMethodID] ASC),
    CONSTRAINT [FK_Payment Method_UserID] FOREIGN KEY ([PaymentMethodID]) REFERENCES [dbo].[User] ([UserID]) ON DELETE CASCADE ON UPDATE CASCADE
);


GO

