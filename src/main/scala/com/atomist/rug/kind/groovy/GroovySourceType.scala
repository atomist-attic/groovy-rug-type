package com.atomist.rug.kind.groovy

import com.atomist.project.ProjectOperationArguments
import com.atomist.rug.kind.core.ProjectMutableView
import com.atomist.rug.kind.dynamic.ContextlessViewFinder
import com.atomist.rug.parser.Selected
import com.atomist.rug.runtime.rugdsl.Evaluator
import com.atomist.rug.spi.{MutableView, ReflectivelyTypedType, Type}
import com.atomist.source.ArtifactSource

/**
  * Created by justin on 1/13/17.
  */
class GroovySourceType(
                  evaluator: Evaluator
                )
  extends Type(evaluator)
    with ContextlessViewFinder
    with ReflectivelyTypedType {

  // TODO: correctly assign manifest type, temporarily set to String to compile
  override def viewManifest: Manifest[_] = manifest[String]

  override type Self = this.type

  // TODO: correctly initialize this, temporarily set to get compile working
  override def resolvesFromNodeTypes: Set[String] = Set("replace", "me")

  override def description: String = "Groovy source file"

  //TODO: correctly implement this, temporary stup implmentation to get compile working
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
