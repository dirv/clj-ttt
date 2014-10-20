(ns clj-ttt.cli-game-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.cli-game :refer :all]))

(describe "outputs a board string"
          (it "outputs an empty game board"
              (should= "1 2 3\n" (board-string "---" 3)))
          (it "splits game board by newline"
              (should= "1 2 3\n4 5 6\n" (board-string "------" 3)))
          (it "shows mark for played square"
              (should= "X\n" (board-string "X" 1)))
          (it "shows mixed game"
              (should= "X O X\n4 5 6\n" (board-string "XOX---" 3))))

(describe "output"
          (it "shows board and next go message"
              (should= "1 2 3\n4 5 6\n7 8 9\nX's go, choose a square: " (display "---------" 3)))
          (it "shows winner"
              (should-contain "X wins!" (display "XXX------" 3))))

(describe "prompt for move"
          (it "gets square to play"
              (should= 2 (with-in-str "3" (read-move)))))


(describe "play one move"
          (it "should prompt for move"
              (should-contain "choose a square: "
                              (with-out-str (with-in-str "1\n" (play-cli-move "---------" 3)))))
          (it "should play move"
              (should= "X--------"
                       (with-in-str "1]\n" (play-cli-move "---------" 3)))))

(describe "play until win"
          (it "should play multiple moves"
              (should-contain "wins!" (with-out-str (with-in-str "6\n3\n4\n5\n"
                                                      (play-until-finish "XX-O-----" 3))))))
