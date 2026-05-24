hubdle {
    install {
        preCommits {
            tests()
            applyFormat()
            assemble()
            checkAnalysis()
            checkApi(false)
            checkFormat()
            dumpApi()
        }
    }
}
