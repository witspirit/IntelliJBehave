#!/bin/sh
FLEX_DIR=~/Projects/intellij/tools/jflex-1.4.3/bin

${FLEX_DIR}/jflex --skel "${FLEX_DIR}/../../idea-flex.skeleton" Story.flex

cat _StoryLexer.java | sed 's/zzBufferL\[zzCurrentPosL++]/zzBufferL.charAt(zzCurrentPosL\+\+)/' > _StoryLexer.tmp

mv _StoryLexer.tmp _StoryLexer.java
