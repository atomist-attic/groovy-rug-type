package com.atomist.rug.kind.groovy

import com.atomist.rug.kind.core.ProjectMutableView
import com.atomist.rug.runtime.rugdsl.Evaluator
import com.atomist.source.file.FileSystemArtifactSource
import com.atomist.source.{FileArtifact, StringFileArtifact}
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class GroovySourceTypeTest extends FlatSpec
  with BeforeAndAfter
  with Matchers {

  var mockEvaluator: Evaluator = _
  var mockMutableView: ProjectMutableView = _
  var mockArtifactSource: FileSystemArtifactSource = _
  var groovyType: GroovySourceType = _

  before {

    mockEvaluator = mock(classOf[Evaluator])
    mockMutableView = mock(classOf[ProjectMutableView])
    mockArtifactSource = mock(classOf[FileSystemArtifactSource])

    groovyType = new GroovySourceType(mockEvaluator)

    val fileArtifacts = Seq[FileArtifact](
      StringFileArtifact.apply("/foo/SomeClass.groovy", "x = 1"),
      StringFileArtifact.apply("/foo/bar/Square.java", "x = 1"),
      StringFileArtifact.apply("/foo/SomeOtherClass.groovy", "y = 1")
    )

    when(mockArtifactSource.allFiles).thenReturn(fileArtifacts)
    when(mockMutableView.currentBackingObject).thenReturn(mockArtifactSource)
  }

  "A GroovySourceType" should "describe itself as a \'Groovy source file\'" in {

    val expected = "Groovy source file"
    val groovyTypeDesc = groovyType.description

    groovyTypeDesc should equal(expected)
  }

  it should "find all groovy source files within a ProjectMutableView" in {

    groovyType.findAllIn(mockMutableView) match {

      case Some(groovyMutableViews) => groovyMutableViews.length should be(2)
      case None => fail()
    }
  }

  it should "create instances of GroovySourceMutableView" in {

    groovyType.findAllIn(mockMutableView) match {

      case Some(groovyMutableViews) => groovyMutableViews foreach { view =>

        view shouldBe a[GroovySourceMutableView]
      }
      case None => fail()
    }
  }
}
