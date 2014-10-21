(ns clj-ttt.computer-player-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.computer-player :refer :all]))

(describe "play move"
          (it "wins a game for X"
              (should= "XXXOO----" (choose-next-move "XX-OO----" "X")))
          (it "wins a game for O"
              (should= "XXO-OXO--" (choose-next-move "XX--OXO--" "O")))
          (it "chooses middle square if it's free"
              (should= "X---O----" (choose-next-move "X--------" "O"))))
