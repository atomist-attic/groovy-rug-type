package com.atomist.rug.kind.groovy

import com.atomist.rug.kind.core.{LazyFileArtifactBackedMutableView, ProjectMutableView}
import com.atomist.source.FileArtifact

/**
  * Created by justin on 1/14/17.
  */
class GroovySourceMutableView(

                               originalBackingObject: FileArtifact,
                               parent: ProjectMutableView)
  extends LazyFileArtifactBackedMutableView(originalBackingObject, parent) {

  override protected def currentContent: String = ???
}
