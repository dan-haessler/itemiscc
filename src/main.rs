use conference_track_management::Talk;
use conference_track_management::Track;
use std::str::FromStr;

fn main() {
  let input = "\
  > Writing Fast Tests Against Enterprise Rails 60min
  > Overdoing it in Python 45min
  > Lua for the Masses 30min
  > Ruby Errors from Mismatched Gem Versions 45min
  > Common Ruby Errors 45min
  > Rails for Python Developers lightning
  > Communicating Over Distance 60min
  > Accounting-Driven Development 45min
  > Woah 30min
  > Sit Down and Write 30min
  > Pair Programming vs Noise 45min
  > Rails Magic 60min
  > Ruby on Rails: Why We Should Move On 60min
  > Clojure Ate Scala (on my project) 45min
  > Programming in the Boondocks of Seattle 30min
  > Ruby vs. Clojure for Back-End Development 30min
  > Ruby on Rails Legacy App Maintenance 60min
  > A World Without HackerNews 30min
  > User Interface CSS in Rails Apps 30min";

  let mut talks: Vec<Talk> = Vec::new();

  for line in input.lines() {
    if let Ok(talk) = Talk::from_str(line) {
      talks.push(talk);
    }
  }

  let tracks: Vec<Track> = organize(talks);
  for i in 0..tracks.len() {
    println!("Track {}\n{}", i + 1, tracks[i]);
  }
}

/// # Since this is reducible to the bin packing problem, it is a np problem.
/// # Greedy algorithm putting longest talk into sessions first.
/// # This does not always lead to an optimal solution but is a good approximation.
fn organize(mut talks: Vec<Talk>) -> Vec<Track> {
  talks.sort_by(|a, b| b.duration.cmp(&a.duration));
  let mut track: Track = Track::new();
  let mut tracks: Vec<Track> = Vec::new();

  while talks.len() > 0 {
    let mut index = 0;
    for _i in 0..talks.len() {
      let talk: &Talk = &talks[index];
      if track.add(talk.clone()) {
        talks.remove(index);
      } else {
        index += 1;
      }
    }
    tracks.push(track);
    track = Track::new();
  }

  tracks
}
