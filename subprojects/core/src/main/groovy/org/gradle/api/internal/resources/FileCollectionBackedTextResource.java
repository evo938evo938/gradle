/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.resources;

import com.google.common.io.Files;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.resources.TextResource;
import org.gradle.api.tasks.TaskDependency;

import java.io.*;
import java.nio.charset.Charset;

public class FileCollectionBackedTextResource implements TextResource {
    private final FileCollection fileCollection;
    private final Charset charset;

    public FileCollectionBackedTextResource(FileCollection fileCollection, Charset charset) {
        this.fileCollection = fileCollection;
        this.charset = charset;
    }

    public String asString() {
        try {
            return Files.toString(asFile(), charset);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Reader asReader() {
        try {
            return Files.newReader(asFile(), charset);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public File asFile() {
        return fileCollection.getSingleFile();
    }

    public TaskDependency getBuildDependencies() {
        return fileCollection.getBuildDependencies();
    }

    public Object getInputProperties() {
        return charset.name();
    }

    public FileCollection getInputFiles() {
        return fileCollection;
    }
}
