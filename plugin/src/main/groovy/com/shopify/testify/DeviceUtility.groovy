package com.shopify.testify

import groovy.io.FileType
import org.gradle.api.Project

class DeviceUtility {

    Project project

    DeviceUtility(Project project) {
        this.project = project
    }

    def getAdbPath() {
        return project.android.getAdbExe().toString()
    }

    def version() {
        def versionLine = [getAdbPath(), '-e', 'shell', 'getprop', 'ro.build.version.sdk']
        return versionLine.execute().text.trim()
    }

    def getDeviceKey() {

        def densityLine = [getAdbPath(), '-e', 'shell', 'wm', 'density']
        def density = densityLine.execute().text.substring("Physical density: ".length()).trim()

        def sizeLine = [getAdbPath(), '-e', 'shell', 'wm', 'size']
        def size = sizeLine.execute().text.substring("Physical size: ".length()).trim()

        return "${version()}-${size}@${density}dp-${language()}"
    }

    def getDeviceImageDirectory() {
        return "/data/data/${project.testify.testContextId}/app_images/"
    }

    def getDestinationImageDirectory() {
        return "${project.testify.baselineSourceDir}/${getDeviceKey()}/"
    }

    def pullScreenshots() {
        println("Copying files...")

        def src = getDeviceImageDirectory() + "."
        def dst = getDestinationImageDirectory()

        println "src:\t${src}"
        println "dst:\t${dst}"

        [getAdbPath(), "-e", 'pull', src, dst].execute()

        // Wait for all the files to be committed to disk
        sleep(project.testify.pullWaitTime);

        println("Ready")
    }

    String[] detectFailedScreenshots() {
        def src = getDeviceImageDirectory();
        def dst = getDestinationImageDirectory();

        ArrayList<String> files = new ArrayList<>();
        def cmd = [getAdbPath(), '-e', 'shell', 'ls', src + '*.png', '2>/dev/null']
        def log = cmd.execute().text
        log.eachLine { line ->
            files.add(line.replace(src, dst));
        }
        return files;
    }

    def removeFilesWithSubstring(String directory, String substring) {
        new File(directory).eachFileRecurse(FileType.FILES) { file ->
            if (file.getName().contains(substring)) {
                println "- Deleting " + file.getName();
                file.delete();
            }
        }
    }

    def generateDiffs() {
        def failedScreenshots = detectFailedScreenshots();
        println("\n\t" + failedScreenshots.size() + " failed screenshot(s) found");

        if (failedScreenshots.length > 0) {
            println("\tGenerating diffs for changed screenshots...");
            for (String filepath in failedScreenshots) {
                def f = new File(filepath);

                def dir = f.parent;
                def parts = f.name.tokenize('.');
                def name = parts[0];
                def ext = parts[1];

                generateDiff filepath, "${filepath} ${dir}/${name}-diff.${ext}"
            }
        }
        println "\n"
    }

    def generateDiff(String src, String dst) {
        File srcFile = new File(src);

        print "\t\tDiff for " + srcFile.getName()

        // Retrieve the current revision from git
        def cmdGit = "git show HEAD:${src}";

        // Write `master` version to tmp file
        String tmpFilePath = srcFile.getParent() + "/master-" + srcFile.getName();
        def outputStream = new FileOutputStream(tmpFilePath);
        cmdGit.execute().waitForProcessOutput(outputStream, System.err);
        outputStream.close()
        print "."

        // Generate comparison image
        def cmdCompare = "compare -compose src ${src} ${tmpFilePath} ${dst}"
        cmdCompare.execute().waitForProcessOutput()
        print "."

        new File(tmpFilePath).delete()
        println "."
    }

    String locale() {
        def command = [getAdbPath(), '-e', 'shell', 'getprop', 'ro.product.locale']
        return command.execute().text.toString().trim();
    }

    String language() {
        if (Integer.parseInt(version()) > 21) {
            String locale = locale()
            return locale.substring(0, locale.indexOf('-'))
        }
        def lang = [getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.language']
        return lang.execute().text.toString().trim()
    }

    def country() {
        if (Integer.parseInt(version()) > 21) {
            String locale = locale()
            return locale.substring(locale.indexOf('-') + 1)
        }
        def country = [getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.country']
        return country.execute().text
    }
}
