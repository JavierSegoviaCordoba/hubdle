plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        documentation {
            changelog()
        }
        
        versioning {
            isEnabled = false
        }
    }
}
