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
        private var options: Options = Options()
        private var parser: CommandLineParser = DefaultParser()
        private var formatter: HelpFormatter = HelpFormatter()
        private var usage: String = (
                "java -jar ploungequoter BACKGND"
        )
        private var head: String = "Generate an image for a Plounge Quote"
        private var footer: String = "Created by /u/CrystalLord (2017)"

        var isHelp: Boolean = false

        fun parse(args: Array<String>) {
            // Use the option builder to create a standard Java-style
            // option.
            var background: Option = (
                    Option.builder("b")
                    .required(true)
                    .longOpt("background")
                    .hasArg()
                    .build()
            )

            this.options.addOption(background)

            try {
                this.cmd = this.parser.parse(options, args)
            } catch (e: UnrecognizedOptionException) {
                // If we used an incorrect option, print the help.
                this.printHelp()
            } catch (e: MissingOptionException) {
                // If we forgot the background, print help.
                this.printHelp()
            }
        }


        fun getBackground(): String? {
            return this.cmd?.getOptionValue("background") ?: null
        }


        fun getConfigFile(): String? {
            return this.cmd?.getOptionValue("configFile") ?: null
        }

        /**
         *
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
