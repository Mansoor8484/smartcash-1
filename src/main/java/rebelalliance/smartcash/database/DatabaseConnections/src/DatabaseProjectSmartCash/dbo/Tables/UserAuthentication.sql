CREATE TABLE [dbo].[UserAuthentication] (
    [UserID]   INT          NOT NULL,
    [Email]    VARCHAR (30) NULL,
    [UserName] VARCHAR (30) NOT NULL,
    [Password] CHAR (30)    NOT NULL,
    CONSTRAINT [PK_UserAuthentication] PRIMARY KEY CLUSTERED ([UserID] ASC),
    CONSTRAINT [FK_UserAuthentication_UserID] FOREIGN KEY ([UserID]) REFERENCES [dbo].[User] ([UserID])
);


GO

