/*
 * Copyright 2015-present wequick.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package net.wequick.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class BuildBundleIdsTask extends DefaultTask {
    Set<String> ids = new HashSet<>()
    @TaskAction
    def run() {
        File buildDir = project.projectDir
        def sTime = System.currentTimeMillis()
        def layoutDir = new File(buildDir, "src/main/res/layout")
        def files = layoutDir.listFiles()
        for (def f : files) {
            findIds(f)
        }
        writeToFile()
        def eTime = System.currentTimeMillis()
        println(buildDir.toString() + "======time:" + (eTime - sTime))
    }

    def writeToFile(){
        File idsFile = new File(project.projectDir,"ids.txt")
        FileWriter fw = new FileWriter(idsFile)
        for(String id:ids){
            fw.write(id)
            fw.write("\r\n")
        }
        fw.flush()
        fw.close()
    }

    def findIds(File layoutFile) {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance()
        XmlPullParser xmlPullParser = factory.newPullParser()
        InputStream inputStream = new FileInputStream(layoutFile)
        xmlPullParser.setInput(inputStream, "UTF-8")
        int eventType = xmlPullParser.getEventType()
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                int count = xmlPullParser.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    String name = xmlPullParser.getAttributeName(i)
                    String value = xmlPullParser.getAttributeValue(i)
                    if(name == 'android:id'){
                        value = value.substring(value.lastIndexOf("/")+1)
                        ids.add(value)
                    }
                }
            }
            eventType = xmlPullParser.next()
        }
    }
}