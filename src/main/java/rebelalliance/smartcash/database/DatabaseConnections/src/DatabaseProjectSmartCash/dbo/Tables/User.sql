CREATE TABLE [dbo].[User] (
    [UserID]    INT          NOT NULL,
    [FirstName] CHAR (20)    NOT NULL,
    [LastName]  CHAR (20)    NOT NULL,
    [Address]   CHAR (30)    NULL,
    [City]      CHAR (25)    NULL,
    [State]     CHAR (2)     NULL,
    [ZipCode]   INT          NULL,
    [CellPhone] CHAR (10)    NULL,
    [Email]     VARCHAR (30) NULL,
    [DOB]       DATE         NULL,
    CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED ([UserID] ASC)
);


GO

