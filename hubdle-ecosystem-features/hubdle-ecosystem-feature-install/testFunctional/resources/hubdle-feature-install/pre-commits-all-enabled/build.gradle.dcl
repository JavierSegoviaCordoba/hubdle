hubdle {
    install {
        preCommits {
            tests()
            applyFormat()
            assemble()
            checkAnalysis()
            checkApi()
            checkFormat()
            dumpApi()
        }
    }
}
