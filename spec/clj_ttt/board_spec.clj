(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.board :refer :all]))

(describe "creating an empty board"
          (it "creates an empty board string"
              (should= "---------" (create-board 3)))
          (it "creates a 4x4 board string"
              (should= "----------------" (create-board 4))))

(describe "playing a move"
          (it "plays an X"
              (should= "--X------" (play-move "---------" 2 "X")))
          (it "only plays a move if square is empty"
              (should= "X" (play-move "X" 0 "O"))))

(describe "winning a game"
          (it "wins a row game"
              (should (won? "XXX------")))
          (it "wins a middle row game"
              (should (won? "---XXX---")))
          (it "wins for O"
              (should (won? "OOO------")))
          (it "does not win"
              (should-not (won? "XXOOOX---")))
          (it "wins on column"
              (should (won? "X--X--X--")))
          (it "wins on diagonal"
              (should (won? "X---X---X"))))

(describe "getting playable squares"
          (it "returns empty list when none available"
              (should= '() (playable-squares "XXX")))
          (it "returns items when some available"
              (should= '(0 1) (playable-squares "--X"))))

(describe "getting next player"
          (it "returns X as first player"
              (should= "X" (next-player "---------"))))
