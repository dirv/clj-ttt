(ns clj-ttt.cli-game-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.cli-game :refer :all]))

(describe "outputs a board string"
          (it "splits game board by newline"
              (should= "1 2 3\n4 5 6\n7 8 9\n" (board-string "---------")))
          (it "shows mark for played square"
              (should= "X\n" (board-string "X")))
          (it "shows mixed game"
              (should= "X O X\n4 5 6\n7 8 9\n" (board-string "XOX------"))))

(describe "output"
          (it "shows board and next go message"
              (should= "1 2 3\n4 5 6\n7 8 9\nX's go, choose a square: " (display "---------")))
          (it "shows winner"
              (should-contain "X wins!" (display "XXX------"))))

(describe "prompt for move"
          (it "gets square to play"
              (should= 2 (with-in-str "3" (read-move)))))


(describe "play one move"
          (it "should prompt for move"
              (should-contain "choose a square: "
                              (with-out-str (with-in-str "1\n" (play-cli-move "---------")))))
          (it "should play move"
              (should= "X--------"
                       (with-in-str "1]\n" (play-cli-move "---------")))))

(describe "play until win"
          (it "should play multiple moves"
              (should-contain "wins!" (with-out-str (with-in-str "6\n3\n4\n5\n"
                                                      (play-until-finish "XX-O-----")))))
          (it "should stop when game is not won"
              (should-contain "It's a draw!" (with-out-str (with-in-str "1\n"
                                                             (play-until-finish "-XOOOXXXO"))))))
