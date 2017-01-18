package com.atomist.rug.kind.groovy

import com.atomist.rug.kind.core.{LazyFileArtifactBackedMutableView, ProjectMutableView}
import com.atomist.rug.spi.TerminalView
import com.atomist.source.FileArtifact

object GroovySourceMutableView

trait GroovyMutableViewNonMutatingFunctions

trait GroovyMutableViewMutatingFunctions

class GroovySourceMutableView(

                               originalBackingObject: FileArtifact,
                               parent: ProjectMutableView)
  extends LazyFileArtifactBackedMutableView(originalBackingObject, parent)
    with TerminalView[FileArtifact]
    with GroovyMutableViewNonMutatingFunctions
    with GroovyMutableViewMutatingFunctions {

  override protected def currentContent: String = content
}
