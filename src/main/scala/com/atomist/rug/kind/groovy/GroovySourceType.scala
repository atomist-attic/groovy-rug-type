package com.atomist.rug.kind.groovy

import com.atomist.project.ProjectOperationArguments
import com.atomist.rug.kind.core.ProjectMutableView
import com.atomist.rug.kind.dynamic.ContextlessViewFinder
import com.atomist.rug.parser.Selected
import com.atomist.rug.runtime.rugdsl.Evaluator
import com.atomist.rug.spi.{MutableView, ReflectivelyTypedType, Type}
import com.atomist.source.ArtifactSource

class GroovySourceType(
                  evaluator: Evaluator
                )
  extends Type(evaluator)
    with ContextlessViewFinder
    with ReflectivelyTypedType {

  override def viewManifest: Manifest[_] = manifest[GroovySourceMutableView]

  override type Self = this.type

  override def resolvesFromNodeTypes: Set[String] = Set("project")

  override def description: String = "Groovy source file"

  override protected def findAllIn(rugAs: ArtifactSource, selected: Selected, context: MutableView[_],
                                   poa: ProjectOperationArguments, identifierMap: Map[String,
    Object]): Option[Seq[MutableView[_]]] = {

    context match {
      case pmv: ProjectMutableView =>
        Some(pmv.currentBackingObject.allFiles
          .filter(f => f.name endsWith ".groovy")
          .map(f => new GroovySourceMutableView(f, pmv))
        )
      case _ => None
    }
  }
}
