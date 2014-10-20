(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.core :refer :all]))

(def ^:const unplayed \-)
(def ^:const row-wins '([0 1 2] [3 4 5] [6 7 8]))
(def ^:const col-wins '([0 3 6] [1 4 7] [2 5 8]))
(def ^:const diag-wins '([0 4 8] [2 4 6]))
(def ^:const all-wins (concat row-wins col-wins diag-wins))

(defn create-board [size]
  (apply str (replicate (* size size) unplayed)))

(defn play-move [board square mark]
   (let [bv (vec board)]
     (if (= unplayed (nth bv square))
       (apply str (assoc bv square mark))
       board)))

(defn distinct-square-values [board squares]
  (distinct (map #(get board %) squares)))

(defn single-player-in-subset? [subset]
  (and (= (count subset) 1) (not= unplayed (first subset))))

(defn won-subset? [board squares]
  (single-player-in-subset? (distinct-square-values board squares)))

(defn won? [board]
  (some #(won-subset? board %) all-wins))

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

