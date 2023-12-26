# TruPax

TruPax generates [VeraCrypt](https://veracrypt.codeplex.com) and TrueCrypt compatible containers from arbitrary sets of files and folders. Such files match\ exactly the size of the contained material and can be mounted via TrueCrypt, but also directly extracted by TruPax itself. Latter also works for containers\ formatted with FAT32 by TrueCrypt itself (thanks to\ [fat32-lib](https://github.com/waldheinz/fat32-lib)). For all of that there are\ no administrator rights required when using TruPax.

The generated file system of the containers is UDF 1.02, which is supported by all of the modern operating systems. Most of them also support writing - meaning files in a container can also be deleted or new ones added. TruPax also wipes files after container generation, or just as a separate action. You also invalidate any TrueCrypt container with it very quickly.

TruPax a command line tool, written in Java 17+. It works fast and also uses all available CPU cores. Containers get generated in just one pass.

The software is free to use and the source code available under the terms of the GPLv3. TruPax is a completely independent implementation of the TrueCrypt logic and shares not a single line of code with latter.

If you want to use the TruPax technology in your own applications, the API is the right starting point.

## Development

Everything is managed by Maven, you need the following dependencies:

* [BaseLib](https://github.com/mchahn/baselib)
* [UDFLib](https://github.com/mchahn/udflib)
* [TCLib](https://github.com/mchahn/tclib)

Clone and mvn build them (added to your local repository) or use the helper script:

    ./deps.sh

To build things in one step simply type:

    ./build.sh

Under Windows you may need to install MSYS to run shell commands.

The preferred development environment is Visual Studio Code - it automatically does the Maven build in the background on every code change. There also are launchers to either just run/debug the code or the fully packaged JAR files, as well as tasks to do the building processes.

## Testing

Verification for TruPax is implemented as JUnit tests, which are partially functional, meaning they do interact with the local file system, and thus run in an authentic manner. All testing material though is created in the temporary directory, no changes to your machine will be made except in there.

To run these test cases:

    DE_ORG_MCHAHN_BASELIB_TEST_QUICK=1 mvn test

The whole set of tests should then execute, a quick test run usually takes less than a minute.

The test cases can also leverage a third party UDF validation tool, called *udf_test*, which can be obtained by Phillips. You need to download this tool yourself, due to licensing it cannot be included in the code base. The *udf_test* software seems to keep moving around, so it might be tricky to find it. Once you acquired it point to the *udf_test* executable via the environment variable *udftestpath*. Under Linux for instance you'd set it to something like */opt/udfct1.5r4/bin/linux-noscsi/udf_test*. The TruPax test cases will then detect its presence and do additional verification steps, to ensure the rendered UDF file systems comply to the actual specification.

## Installation

Follow the script for the MacOS/Linux version:

    cd etc
    ./install_macos.sh /usr/bin # change the installation directory if needed

After these basic files have been copied over and you made sure that Java is available:

    trupax

It will show you the command line options available and all about the usage.

For Windows proceed similarly, using the _trupax.cmd_ script.

## Usage

TruPax is used on the command line. For Windows that's the command
prompt, under Linux the terminal or bash shell respectively. For Linux
the script _install.sh_ (see above) automatically installs command line
availability on the whole system. Under Window it is recommended to add the
path to where the file _trupax.cmd_ is located to the environment variable _PATH_.

The actual usage is the same for every operating system. With a simple call to
it TruPax shows you help about syntax and all its options:

    trupax
    (MISSING_CMDLN_ARG): Not enough parameters. (no details available)
    ...

Files and folder selections are passed in the call.
After password input and confirmation the container gets created. Extraction
with the option `--extract` and optional wiping of the files through
`--wipe` or just wiping of a selection via `--wipe-only` are also
possible.

By using the parameter `--password` you can also avoid password input to
happen while the program is running, so TruPax can be used in fully automated
scenarios (e.g. for archiving purposes).

If a file or folder starts with a dash you can replace it (the dash) with a
triple dash, so it doesn't get interpreted as an option. For instance a file
called `-index` would be declared as `---index`.

TruPax can be interrupted through the common abort key combination of the
operating system it is running on (in most cases that is _Ctrl+C_).

## Configuration

The configuration file named _trupax.properties_ is used. You can modify it to set custom default settings. Check out the example _etc/trupax_example.properties_ for details.

## Exit Codes

TruPax defines certain exit codes for the command line, so calling instances can
check them. For instance to verify if an operation was successful or (if not)
what went wrong. If an error occurred the name of it gets shown, yet the actual
exit code value is one of the following numbers:

* 0 - The operation was successful (or as expected).
* 1 - An internal or unexpected error happened.
* 2 - Program error, e.g. if there is no text console available.
* 3 - A self test for an algorithm failed. TruPax should be reinstalled in such a case.
* 4 - The user cancelled the operation.
* 5 - One or more command line parameter is invalid.
* 6 - One or more command line parameters are missing.
* 7 - Files or folders couldn't be registered completely.
* 8 - The configuration file couldn't be loaded.
* 10 - Container preparation failed, e.g. if the some file paths are too long.
* 11 - Container initialization failed, it might already exist or be write protected.
* 13 - An error occurred during container creation.
* 14 - The selection of files contains name collisions due to path or file overlaps.
* 15 - The container file exists already and wasn't overwritten or even touched.
* 16 - An unknown entry was found in the configuration file.
* 17 - An invalid entry was found in the configuration file.
* 20 - The container couldn't be opened for extraction.
* 21 - The container couldn't be decrypted.
* 22 - An error occurred during extraction.
* 23 - The container file couldn't be invalidated.

## Noteworthy

* The maximum size of a container is 1,099,511,627,264 bytes (roughly one TB).
* The maximum allowed length the name of a file or folder is 255 (ASCII), or 127 if Unicode is needed.
* The maximum length of a path is 1023.
* The maximum size of a file in a container is 45,097,135,104 bytes (around 45GB).
* For very small UDF containers (less than 100kB) mounting fails under Linux.
* The encryption algorithm is AES-256.
* The hash algorithm is SHA-512.
* Container files are created with a second (backup) header.
* Containers created with TruPax are compatible with TrueCrypt 6+ or VeraCrypt 1.14+.

## API

There are two levels of API:

* *de.org.mchahn.trupax.lib.prg.Prg(Impl)* -
  the official TruPax API, as used by the command line tool
* *de.org.mchahn.tclib* -
  low-level functionality, for crypto and raw container handling

Both parts are thoroughly covered with Javadoc. The best way to learn about the usage though is to look at the samples:

* *de.org.mchahn.trupax.sdk.demos* - various examples on how to use the API
* *de.org.mchahn.trupax.sdk.apps*  - command line apps based on the low-level API
