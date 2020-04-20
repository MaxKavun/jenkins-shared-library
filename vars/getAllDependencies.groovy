#!/usr/bin/env groovy

//import java.security.MessageDigest
//import java.security.NoSuchAlgorithmException
//import hudson.FilePath - potentially better realisation without reading file & embedded method which return md5 hash sum
import hudson.model.*
import com.max.jsl.*

def call(buildSettings, workspace) {
    def COMMA_OR_COLON = "[,:]"
    def artifactsClassPath = readFile "${workspace}/target/classpath"
    for (def artifact : artifactsClassPath.split(COMMA_OR_COLON)) {
        def artifactFile = sh(script: "md5sum ${artifact}", returnStdout: true)
        def jobLink = checkForFingerprints(artifactFile.split()[0], buildSettings)
        if (jobLink)
            buildSettings.upstreamDependencies.add(jobLink)
    }
    print buildSettings.upstreamDependencies
}

@NonCPS
def checkForFingerprints(artifact, buildSettings) {
    FingerprintMap map = Jenkins.get().getFingerprintMap()
    Fingerprint myFingerprint = map.get(artifact)
    if (!myFingerprint) return
    return myFingerprint.getOriginal().getName()
}

/*
@NonCPS
def generateMd5(artifact) {
    MessageDigest digest = MessageDigest.getInstance('MD5')
    digest.update(artifact.bytes, 0, artifact.length())
    md5 = new BigInteger(1, digest.digest()).toString(16)
    return md5
}*/