# 4348-Project-1
The driver program will accept a single command line argument that has the name of the log file. The driver is in charge of communicating and recieving data from the other two programs and provides the menu for the user to select what they want to do.

The logger will record any activity the system does with time stamps. Takes a log filename as a command line argument. Will do this until QUIT is typed in.

The Encryption program handles setting the passkey and creating the cipher and decoding the cipher. Accepts three commands via input.

To compile the files go to the directory where the files are located and type in all in one line javac Logger.java Encryption.java Driver.java 

After compiling run the program Driver.java with the command line argument:
java Driver<Log filename>.txt log filename is whatever you would like the filename to be this is where the system info will be logged.

Once running the driver program will show a menu that has five options:

1.PASSWORD allows the user to set or select the password

2. ENCRYPT allows the user to encrypt a string using the current passkey either encrypting a old string or a new one

3. DECRYPT allows the user to decrypt a string using the current passkey also allows user to choose whether to decrypt old string or new string

4. HISTORY allows users to view all previously entered or generated strings

5. QUIT 

Important Notes: All programs must be in the same directory when running.


