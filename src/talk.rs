use std::{time::Duration, num::ParseIntError, fmt};

#[derive(Debug, PartialEq, Eq)]
pub struct Talk {
  description: String,
  duration: Duration,
}

/// ```
/// use std::time::Duration;
/// use conference_track_management::Talk;
///
/// let firstInput = "> Common Ruby Errors 45min";
/// let secondInput = "> Rails for Python Developers lightning";
/// let thirdInput = "> Communicating Over Distance 60min";
/// 
/// let firstEx = Talk::new("Common Ruby Errors".to_string(), Duration::from_secs(45 * 60));
/// let secondEx = Talk::new("Rails for Python Developers".to_string(), Duration::from_secs(300));
/// let thirdEx = Talk::new("Communicating Over Distance".to_string(), Duration::from_secs(60 * 60));
/// 
/// assert_eq!(Talk::from_string(firstInput), Ok(firstEx));
/// assert_eq!(Talk::from_string(secondInput), Ok(secondEx));
/// assert_eq!(Talk::from_string(thirdInput), Ok(thirdEx));
/// ```
impl Talk {
  pub fn new(description: String, duration: Duration) -> Talk {
    Talk { description, duration }
  }

  pub fn from_string(string: &str) -> Result<Talk, ParseIntError> {
    let mut description: String = String::new();
    let mut duration: Duration = Duration::from_secs(300);
    let words: Vec<&str> = string.split_whitespace().collect();
    for i in 1..(words.len() - 1) {
      description += words[i];
      description += " ";
    }
    description.pop();
    let last_word: &str = words[words.len() - 1];
    if last_word.contains("min") {
      match last_word.replace("min", "").parse::<u64>() {
        Ok(minutes) => duration = Duration::from_secs(minutes * 60),
        Err(e) => return Err(e)
      }
    }
    Ok(Talk::new(description, duration))
  }
}

impl fmt::Display for Talk {
  fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
    write!(f, "{} {}min", self.description, (self.duration.as_secs() / 60))
  }
}
