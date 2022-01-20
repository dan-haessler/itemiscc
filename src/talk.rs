use std::{num::ParseIntError, fmt};
use std::str::FromStr;
use chrono::Duration;

/// # A talk of the big programming conference.
/// A talk contains a description/talk title and a duration.
/// 
/// ```
/// use chrono::Duration;
/// use conference_track_management::Talk;
/// use std::str::FromStr;
///
/// let firstInput = "> Common Ruby Errors 45min";
/// let secondInput = "> Rails for Python Developers lightning";
/// let thirdInput = "> Communicating Over Distance 60min";
/// 
/// let firstEx = Talk::new("Common Ruby Errors".to_string(), Duration::minutes(45));
/// let secondEx = Talk::new("Rails for Python Developers".to_string(), Duration::minutes(5));
/// let thirdEx = Talk::new("Communicating Over Distance".to_string(), Duration::minutes(60));
/// 
/// assert_eq!(Talk::from_str(firstInput), Ok(firstEx));
/// assert_eq!(Talk::from_str(secondInput), Ok(secondEx));
/// assert_eq!(Talk::from_str(thirdInput), Ok(thirdEx));
/// ```
#[derive(Debug, PartialEq, Eq, Clone)]
pub struct Talk {
  pub description: String,
  pub duration: Duration,
}

impl Talk {
  pub fn new(description: String, duration: Duration) -> Talk {
    Talk { description, duration }
  }
}

/// ```
/// use chrono::Duration;
/// use conference_track_management::Talk;
/// 
/// assert_eq!(format!("{}", Talk::new("Test".to_string(), Duration::minutes(45))), "Test 45min");
/// assert_eq!(format!("{}", Talk::new("Light Test".to_string(), Duration::minutes(5))), "Light Test lightning");
/// assert_eq!(format!("{}", Talk::new("Harder Test".to_string(), Duration::minutes(60))), "Harder Test 60min");
/// assert_eq!(format!("{}", Talk::new("Hardest Test".to_string(), Duration::minutes(60))), "Hardest Test 60min");
/// ```
impl fmt::Display for Talk {
  fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
    let minutes = self.duration.num_minutes();
    if minutes == 5 {
      write!(f, "{} {}", self.description, "lightning")
    } else {
      write!(f, "{} {}min", self.description, minutes)
    }
  }
}

/// Parses an example string to a talk.
/// 
/// ```
/// use chrono::Duration;
/// use conference_track_management::Talk;
/// use std::str::FromStr;
/// 
/// let firstEx = Talk::new("Test".to_string(), Duration::minutes(45));
/// let secondEx = Talk::new("Light Test".to_string(), Duration::minutes(5));
/// let thirdEx = Talk::new("Harder Test".to_string(), Duration::minutes(60));
/// let fourthEx = Talk::new("Hardest Test".to_string(), Duration::minutes(60));
/// 
/// assert_eq!(Talk::from_str("> Test 45min"), Ok(firstEx));
/// assert_eq!(Talk::from_str("> Light Test lightning"), Ok(secondEx.clone()));
/// assert_eq!(Talk::from_str("Light lightning Test"), Ok(secondEx));
/// assert_eq!(Talk::from_str("> 60min Harder Test"), Ok(thirdEx));
/// assert_eq!(Talk::from_str("60min Hardest Test"), Ok(fourthEx.clone()));
/// assert_eq!(Talk::from_str("Hardest 60min Test"), Ok(fourthEx.clone()));
/// ```
impl FromStr for Talk {
  type Err = ParseIntError;

  fn from_str(s: &str) -> Result<Self, Self::Err> {
    let mut description: String = String::new();
    let mut duration: Duration = Duration::minutes(5);
    let words: Vec<&str> = s.split_whitespace().collect();
    for i in 0..words.len() {
      let word = words[i];
      // Skip command line char.
      if word.contains(">") {
        continue;
      } else if word.contains("lightning") {
        continue;
      } else if word.contains("min") {
        // Is probably talk duration, check for numbers.
        let number_candidate = word.replace("min", "");
        let mut is_numeric = true;
        for c in number_candidate.chars() {
          if !c.is_numeric() {
            is_numeric = false;
          }
        }
        if is_numeric {
          let minutes: i64 =  number_candidate.parse::<i64>()?;
          duration = Duration::minutes(minutes);
          continue;
        }
      }
      description += words[i];
      description += " ";
    }
    description.pop();
    Ok(Talk::new(description, duration))
  }
}
