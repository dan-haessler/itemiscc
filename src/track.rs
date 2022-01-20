use crate::Session;
use crate::Talk;
use std::fmt;
use chrono::NaiveTime;

/// # A track of the conference.
/// A track contains a morning and afternoon session, talks can be added if they fit the schedule.
///
/// ```
/// use chrono::{Duration};
/// use conference_track_management::{Talk, Track};
/// 
/// let mut track = Track::new();
///
/// let morningTalk = Talk::new("Filling morning session".to_string(), Duration::minutes(180));
/// let afternoonTalk = Talk::new("Filling afternoon session".to_string(), Duration::minutes(240));
/// let overflowTalk = Talk::new("Overflowing talk".to_string(), Duration::minutes(60));
/// 
/// let morningFit = track.add(morningTalk);
/// let afternoonFit = track.add(afternoonTalk);
/// let overflowTalkFit = track.add(overflowTalk);
/// 
/// assert_eq!(morningFit, true);
/// assert_eq!(afternoonFit, true);
/// assert_eq!(overflowTalkFit, false);
/// ```
pub struct Track {
  morning: Session,
  afternoon: Session
}

impl Track {
  pub fn new() -> Track {
    Track {
      morning: Session::new(NaiveTime::from_hms(9, 0, 0), NaiveTime::from_hms(12, 0, 0)),
      afternoon: Session::new(NaiveTime::from_hms(13, 0, 0), NaiveTime::from_hms(17, 0, 0))
    }
  }

  /// Tries to add talk to morning session if it fits, checks for afternoon session after.
  /// Returns true if added, false otherwise.
  pub fn add(&mut self, talk: Talk) -> bool {
    if self.morning.fits(&talk) {
      self.morning.add(talk);
      true
    } else if self.afternoon.fits(&talk) {
      self.afternoon.add(talk);
      true
    } else {
      false
    }
  }

  /// Returns whether it fits in morning or afternoon session.
  pub fn fits(&self, talk: &Talk) -> bool {
    self.morning.fits(talk) || self.afternoon.fits(talk)
  } 
}

impl fmt::Display for Track {
  fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
    write!(f, "{}{} Lunch\n{}{} Networking Event\n",
      self.morning, self.morning.get_end_time(),
      self.afternoon, self.afternoon.get_end_time())
  }
}
