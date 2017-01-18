package com.atomist.rug.kind.groovy


import com.atomist.rug.kind.core.ProjectMutableView
import com.atomist.rug.runtime.rugdsl.Evaluator
import com.atomist.source.{FileArtifact, StringFileArtifact}
import com.atomist.source.file.FileSystemArtifactSource
import org.hamcrest.CoreMatchers._
import org.hamcrest.MatcherAssert._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, FlatSpec}

/**
  * Created by justin on 1/13/17.
  */
class GroovySourceTypeTest extends FlatSpec with BeforeAndAfter {

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

  "A GroovySourceType" should "describe itself accurately as \'Groovy source file\'" in {

    val expected = "Groovy source file"
    val groovyTypeDesc = groovyType.description

    assertThat(groovyTypeDesc, equalTo(expected))
  }

  it should "find all groovy source files within a ProjectMutableView" in {

    groovyType.findAllIn(mockMutableView) match {

      case Some(groovyMutableViews) => assertThat(groovyMutableViews.length, equalTo(2))
      case None => fail()
    }
  }

  it should "create instances of GroovySourceMutableView" in {

    groovyType.findAllIn(mockMutableView) match {

      case Some(groovyMutableViews) => groovyMutableViews foreach {

        case a: GroovySourceMutableView => //this is good
        case _ => assertThat(true, equalTo(false))
      }
      case None => assertThat(true, equalTo(false))
    }
  }
}
