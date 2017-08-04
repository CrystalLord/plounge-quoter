package org.crystal.ploungequoter

import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.UnrecognizedOptionException
import org.apache.commons.cli.MissingOptionException

/**
 * Parsing Singleton to handle command line arguments.
 */
class PQParser {
    companion object {
        private var cmd: CommandLine? = null
        private val options: Options = Options()
        private val parser: CommandLineParser = DefaultParser()
        private var formatter: HelpFormatter = HelpFormatter()
        private val usage: String = (
                "java -jar ploungequoter.jar [-h][-f][-b BACKGND]"
        )
        private val head: String = "Generate an image for a Plounge Quote"
        private val footer: String = "Created by /u/CrystalLord (2017)"

        /** Should we print out the available fonts? */
        var showFonts: Boolean = false
        /** Did the user ask for help? */
        var isHelp: Boolean = false

        fun parse(args: Array<String>) {
            // Use the option builder to create a standard Java-style
            // option.
            val help: Option =
                    Option.builder("h")
                    .longOpt("help")
                    .build()
            val background: Option =
                    Option.builder("b")
                    .longOpt("background")
                    .hasArg()
                    .build()
            val quoteFile: Option =
                    Option.builder("q")
                    .longOpt("quotefile")
                    .hasArg()
                    .build()
            val showFonts: Option =
                    Option.builder("f")
                    .longOpt("fonts")
                    .build()

            // Add each option to the options object so that we can parse
            // them correctly.
            this.options.addOption(help)
            this.options.addOption(background)
            this.options.addOption(quoteFile)
            this.options.addOption(showFonts)

            try {
                this.cmd = this.parser.parse(options, args)
                // Check if asked for help
                if (this.cmd?.hasOption('h') ?: false) {
                    this.isHelp = true
                    this.printHelp()
                } else if (this.cmd?.hasOption("fonts") ?: false) {
                    this.showFonts = true
                } else if (
                        !(this.cmd?.hasOption('b') ?: false)
                        && !(this.cmd?.hasOption('q') ?: false)) {
                    this.isHelp = true
                    this.printHelp()
                }
            } catch (e: UnrecognizedOptionException) {
                // If we used an incorrect option, print the help.
                this.printHelp()
            } catch (e: MissingOptionException) {
                // If we forgot something, print help.
                this.printHelp()
            }
        }

        /** Retrieve the background image path if set. Otherwise return null */
        fun getBackground(): String? {
            return this.cmd?.getOptionValue("background")
        }

        /** Retrieve the quote file path if set. Otherwise return null */
        fun getQuoteFile(): String? {
            return this.cmd?.getOptionValue("quotefile")
        }

        /**
         * Print out the usage and help of the program.
         */
        fun printHelp() {
            this.isHelp = true
            this.formatter.printHelp(
                    79,
                    this.usage,
                    this.head,
                    this.options,
                    this.footer
            )

        }
    }

}
