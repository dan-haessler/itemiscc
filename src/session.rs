use std::fmt;
use chrono::{Duration, NaiveTime};
use crate::Talk;

/// # Session of the conference.
/// A session with start and end time with a Vec containing talks to represent morning/afternoon sessions.
/// 
/// ```
/// use chrono::{Duration, NaiveTime};
/// use conference_track_management::{Talk, Session};
/// 
/// let mut session = Session::new(NaiveTime::from_hms(9, 0, 0), NaiveTime::from_hms(11, 0, 0));
///
/// let firstTalk = Talk::new("Common Ruby Errors".to_string(), Duration::minutes(45));
/// let secondTalk = Talk::new("Rails for Python Developers".to_string(), Duration::minutes(5));
/// let thirdTalk = Talk::new("Communicating Over Distance".to_string(), Duration::minutes(60));
/// let longTalk = Talk::new("A long talk".to_string(), Duration::minutes(60));
/// let shortTalk = Talk::new("A short talk".to_string(), Duration::minutes(10));
/// 
/// session.add(firstTalk);
/// session.add(secondTalk);
/// session.add(thirdTalk);
/// 
/// assert_eq!(session.get_talk_duration(), Duration::minutes(110));
/// assert_eq!(session.get_total_time(), Duration::minutes(120));
/// assert_eq!(session.fits(&shortTalk), true);
/// assert_eq!(session.fits(&longTalk), false);
/// ```
pub struct Session {
  start: NaiveTime,
  end: NaiveTime,
  talks: Vec<Talk>,
}

impl Session {
  /// Creates a new session with given start and end time.
  pub fn new(start: NaiveTime, end: NaiveTime) -> Session {
    Session {
      start: start,
      end: end,
      talks: Vec::new()
    }
  }

  /// Adds talk to session if it fits.
  pub fn add(&mut self, talk: Talk) {
    if self.fits(&talk) {
      self.talks.push(talk);
    }
  }

  /// Does fit in the session time.
  pub fn fits(&self, talk: &Talk) -> bool {
    self.get_talk_duration() + talk.duration <= self.get_total_time()
  }

  /// Total time of the session.
  pub fn get_total_time(&self) -> Duration {
    self.end.signed_duration_since(self.start)
  }

  /// End time of session.
  pub fn get_end_time(&self) -> NaiveTime {
    self.end
  }

  /// Get summed duration of current talks.
  pub fn get_talk_duration(&self) -> Duration {
    let mut talk_duration: Duration = Duration::minutes(0);
    for talk in &self.talks {
      talk_duration = talk_duration + talk.duration;
    }
    talk_duration
  }
}

impl fmt::Display for Session {
  fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
    let mut current_time = self.start;
    let mut output: String = String::new();
    for talk in &self.talks {
      output.push_str(&format!("{} {}\n", current_time, talk));
      current_time += talk.duration;
    }
    write!(f, "{}", output)
  }
}
